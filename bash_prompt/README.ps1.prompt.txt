## http://jqno.nl/post/2012/04/02/howto-display-the-current-git-branch-in-your-prompt/
##
## http://ezprompt.net/
##
## https://unix.stackexchange.com/questions/25903/awesome-symbols-and-characters-in-a-bash-prompt
##
## https://r12a.github.io/apps/conversion/
## http://jrgraphix.net/r/Unicode/25A0-25FF
##

source git-completion.bash
source git-prompt.sh

simple:
PS1='\u $(__git_ps1 "(%s)")\$ '

color awful:
PS1='\[\033[32m\]\u@\h\[\033[00m\]:\[\033[34m\]\w\[\033[31m\]$(__git_ps1)\[\033[00m\]\$ '

just ok:
PS1="\[\e[30;42m\]\u@\h \[\e[30;46m\]\w \[\e[m\] \n\[\e[0;32m\]└─\[\e[0;32m\] \t \$ \[\e[0;32m\]▶ \[\e[m\] "


COLOR_EXIT_CODE="\[\e[0;32m\]"
COLOR_EXIT_CODE="\[\e[0;31m\]"
PS1="\[\e[30;42m\]\u@\h \[\e[30;46m\]\w \[\e[30;43m\]$MY_GIT_PROMPT \[\e[m\] \n\[\e[0;32m\]└─\[\e[0;32m\] \t \$ ${COLOR_EXIT_CODE}▶ \[\e[m\] "
PS1="\[\e[30;42m\]\u@\h \[\e[30;46m\]\w \[\e[m\] \n\[\e[0;32m\]└─\[\e[0;32m\] \t \$ ${COLOR_EXIT_CODE}▶ \[\e[m\] "


reset: $(tput init)


special characters:
PS1='\['"`tput sc`"'\]  \['"`tput rc`"'༇ \] \$ '


red=$(tput setaf 1)
green=$(tput setaf 2)
yellow=$(tput setaf 3)
reset=$(tput sgr0)
PS1='\[$red\]\u\[$reset\]@\[$green\]\h\[$reset\]:\[$blue\]\w\[$reset\]\$ '


reset: [\e[m\]
yellow: \[\e[30;43m\]


git rev-parse --abbrev-ref HEAD 2>/dev/null


PS1="\[\e[30;42m\]\u\[\e[m\] fix $


# reset: $(tput init) ▶▶▶▶▶▶▶▶▶▶
# PS1='\u \$ '
# printf '\e[38;5;0m\e[48;5;36m mue@localhost \e[38;5;0m\e[48;5;69m /tmp/xxx \e[38;5;0m\e[48;5;214m git: master \e[\033[0m\n'



## special unicode characters
##

PS1=$'\[\e[31m\]\xe2\x88\xb4\[\e[0m\]\n\xe2\x86\x92 \xe2\x98\xbf \~ \[\e[31m\\]\xe2\x98\x85 $? \[\e[0m\]'

##
# You can see the hexidecimal values by running echo ∴ → ☿ ★ | hexdump -C in a UTF-8 terminal, 
# e.g. ∴ is encoded by \xe2\x88\xb4 in UTF-8.
#
echo ∴ | hexdump -C
00000000  e2 88 b4 0a                                       |....|
00000004

PS1=$'\[\e[31m\]\xe2\x88\xb4\[\e[0m\] '


U+25E4
32 35 65 34

U25E2
32 35 65 32

## not working... why !!!
##
PS1=$'\\[\e[31m\\]\x32\x35\x65\x34\\[\e[0m\\] '




print_before_the_prompt () {
    printf "\n$txtwht%s: $bldgrn%s [branch:$txtylw%s]\n$txtrst" "$USER" "$PWD" "$(git rev-parse --abbrev-ref HEAD)"
}
 
PROMPT_COMMAND=print_before_the_prompt
PS1="\t| 💩 -> "



## actual active ************************************************* change-git-prompt.bash

## things to do:
# enhance color handling
# make it more eye candy
# change when root

my_prompt_pre_evaluate () {
	local MY_ACTUAL_GIT_BRANCH=$(git rev-parse --abbrev-ref HEAD 2>/dev/null)
	export MY_GIT_PROMPT=""
    if [[ "$MY_ACTUAL_GIT_BRANCH" != "" ]]; then
		MY_GIT_PROMPT="git: $MY_ACTUAL_GIT_BRANCH"
		PS1="\[\e[30;42m\]\u@\h \[\e[m\]\[\e[30;46m\]\w \[\e[30;43m\]$MY_GIT_PROMPT \[\e[m\] \n\[\033[0;32m\]└─\[\033[0m\033[0;32m\] \t \$\[\033[0m\033[0;32m\] ▶ \[\033[0m\] "

	else
		PS1="\[\e[30;42m\]\u@\h \[\e[m\]\[\e[30;46m\]\w \[\e[m\] \n\[\033[0;32m\]└─\[\033[0m\033[0;32m\] \t \$\[\033[0m\033[0;32m\] ▶ \[\033[0m\] "
	fi

}
 
PROMPT_COMMAND=my_prompt_pre_evaluate
 
# reset: $(tput init) 
# PS1='\u \$ '



## list all colors ********************************************* colors.sh

#!/bin/bash
for((i=16; i<256; i++)); do
    printf "\e[48;5;${i}m%03d" $i;
    printf '\e[0m';
    [ ! $((($i - 15) % 6)) -eq 0 ] && printf ' ' || printf '\n'
done


## list all colors ********************************************* PS1-colors.sh

#!/bin/bash
#
#   This file echoes a bunch of color codes to the 
#   terminal to demonstrate what's available.  Each 
#   line is the color code of one forground color,
#   out of 17 (default + 16 escapes), followed by a 
#   test use of that color on all nine background 
#   colors (default + 8 escapes).
#

T='gYw'   # The test text

echo -e "\n                 40m     41m     42m     43m\
     44m     45m     46m     47m";

for FGs in '    m' '   1m' '  30m' '1;30m' '  31m' '1;31m' '  32m' \
           '1;32m' '  33m' '1;33m' '  34m' '1;34m' '  35m' '1;35m' \
           '  36m' '1;36m' '  37m' '1;37m';
  do FG=${FGs// /}
  echo -en " $FGs \033[$FG  $T  "
  for BG in 40m 41m 42m 43m 44m 45m 46m 47m;
    do echo -en "$EINS \033[$FG\033[$BG  $T  \033[0m";
  done
  echo;
done
echo


## *************************************************************

Depending on whether you want to apply the color to the foreground or to the background, use an <fg_bg> value 
of 38 or 48 (respectively) in the following command:

printf '\e[<fg_bg>;5;<ANSI_color_code>m'

For example, to set the foreground color (<fg_bg>=38) to red (<ANSI_color_code>=196) and the background color 
(<fg_bg>=48) to black (<ANSI_color_code>=0):

printf '\e[38;5;196m Foreground color: red\n'
printf '\e[48;5;0m Background color: black\n'

printf '\e[38;5;0m\e[48;5;214m Example Text Color \e[\033[0m\n'
printf '\e[38;5;0m\e[48;5;36m mue@localhost \e[38;5;0m\e[48;5;69m /tmp/xxx \e[38;5;0m\e[48;5;214m git: master \e[\033[0m\n'

