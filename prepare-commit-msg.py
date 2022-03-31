#!/usr/bin/env python
import sys, re
import curses
from subprocess import check_output

jiraprefix = 'LOFIOS-'
def selectOption(stdscr, classes, menuheader):
    attributes = {}
    curses.init_pair(1, curses.COLOR_WHITE, curses.COLOR_BLACK)
    attributes['normal'] = curses.color_pair(1)

    curses.init_pair(2, curses.COLOR_BLACK, curses.COLOR_WHITE)
    attributes['highlighted'] = curses.color_pair(2)

    c = 0  # last character read
    option = 0  # the current option that is marked
    while c != 10:  # Enter key in ascii to exit
        stdscr.erase()
        stdscr.addstr(menuheader + "\n", curses.A_UNDERLINE)
        for i in range(len(classes)):
            if i == option:
                attr = attributes['highlighted']
            else:
                attr = attributes['normal']
            stdscr.addstr("{0}. ".format(i + 1))
            stdscr.addstr(classes[i] + '\n', attr)
        c = stdscr.getch()
        if c == curses.KEY_UP:
            if option > 0:
                option -= 1
            else:
                option = len(classes) - 1
        elif c == curses.KEY_DOWN:
            if option < len(classes) - 1:
                option += 1
            else:
                option = 0

    return classes[option]


def typeofchange(stdscr):
    classes = [ "feat:     Add a new feature (equivalent to a MINOR in Semantic Versioning)",
                "fix:      Fix a bug (equivalent to a PATCH in Semantic Versioning)",
                "docs:     Documentation changes",
                "style:    Code style change (semicolon, indentation...)",
                "refactor: Refactor code without changing public API",
                "perf:     Update code performances",
                "test:     Add test to an existing feature",
                "chore:    Update something without impacting the user (ex: bump a dependency in build)",
                "revert:   Revert to a commit",
                "WIP:     Work in progress"]
    header = "Select the type of change that you're committing: (Use arrow keys)"
    return selectOption(stdscr, classes, header)

def typeofscope(stdscr):
    classes = ["test", "rules", "design", "style", "code", "empty", "custom"]
    header = "Denote the SCOPE of this change (optional): (Use arrow keys)"
    return selectOption(stdscr, classes, header)

def findscope(scope):
    if scope == 'empty':
        scope = ''
    elif scope == 'custom':
        scope = raw_input(bcolors.OKGREEN + "Enter the SCOPE of this change: " + bcolors.ENDC)
    return '(' + scope + ')' if scope != '' else scope

class bcolors:
    HEADER      = '\033[95m'
    OKBLUE      = '\033[94m'
    OKGREEN     = '\033[92m'
    WARNING     = '\033[93m'
    FAIL        = '\033[91m'
    ENDC        = '\033[0m'
    BOLD        = '\033[1m'
    UNDERLINE   = '\033[4m'

branch_info = 'Branch name must starts with a valid branch type followed by valid JIRA-ID\n\
Types of branch - \n\
    feature:    Adding a new feature\n\
    bugfix:     Fixing a bug\n\
    hotfix:     Hotfix for a release\n\
    Rename branch command: git branch -m "<new-branch-name>"\n\
    ' + bcolors.FAIL + 'eg. => feature/'+ jiraprefix + '199-UserAgent' + bcolors.ENDC

branch_regex = '(feature|bugfix|hotfix)\/('+ jiraprefix + '\d+)'
commit_regex = '^(revert: )?(feat|fix|docs|style|refactor|perf|test|chore)(\(.+\))?: .{1,50}'


commit_msg_filepath = sys.argv[1]
branch = check_output(['git', 'symbolic-ref', '--short', 'HEAD']).strip()

if re.match(branch_regex, branch):
    issue = re.match(branch_regex, branch).group(2)
    committype = curses.wrapper(typeofchange).split(':')[0]
    scope = findscope(curses.wrapper(typeofscope))
    with open(commit_msg_filepath, 'r+') as fh:
        commit_msg = fh.read()
        if not commit_msg.strip():
            print bcolors.WARNING + 'Incorrect commit message, please insert a commit message using: ' + bcolors.BOLD + 'git commit -m <commit-msg>' + bcolors.ENDC
            print bcolors.FAIL + 'prepare-commit-msg - ' + bcolors.BOLD + 'FAILED' + bcolors.ENDC
            sys.exit(1)
        else:
            moreissues = raw_input(bcolors.OKGREEN + '\nList any ISSUES CLOSED by this change (optional). E.g.: ' + jiraprefix + '230, ' + jiraprefix + '180:\n' + bcolors.ENDC)
            if moreissues.strip() and moreissues.strip() != issue.strip():
                issue = issue + ', ' + moreissues
            more_msg = raw_input(bcolors.OKGREEN + '\nAny more detailed message to add (optional): \n' + bcolors.ENDC)
            if more_msg.strip():
                commit_msg = commit_msg + '\n* Commit detail: ' + more_msg + '\n'
            
            commit_msg = "[{}] {}{}: {}".format(issue, committype, scope, commit_msg)
            print bcolors.OKBLUE + '\n###--------------------------------------------------------###\
            \n\n' + commit_msg + '\n\n###--------------------------------------------------------###\n' + bcolors.ENDC
            proceed = raw_input(bcolors.OKGREEN + 'Are you sure you want to proceed with the commit above? (y) \n' + bcolors.ENDC)
            if proceed in ('y', 'Y', 'yes', ' YES', ''):
                if proceed == '':
                    print "Y"
                fh.seek(0, 0)
                fh.write(commit_msg)
            else:
                print bcolors.FAIL + 'Commit has been canceled.' + bcolors.ENDC
                sys.exit(1)
elif branch != 'master' and branch != 'release':
    print bcolors.WARNING + 'Incorrect branch name: ' + bcolors.BOLD + '' + branch + bcolors.ENDC
    print bcolors.FAIL + 'prepare-commit-msg - ' + bcolors.BOLD + 'FAILED' + bcolors.ENDC
    print branch_info
    sys.exit(1)
