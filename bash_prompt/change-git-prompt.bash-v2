## things to do:
# enhance color handling
# make it more eye candy
# change when root

## history:
## --------
# v2   reduced escape sequences
#      colored red feedback when an error occured (non 0 exit value) see COLOR_EXIT_CODE
# v1   initial

my_prompt_pre_evaluate () {
	## this must me the first thing defined in that function or the $? will evaluate not the last external call
	## color if last exit status is non 0
	##
    if [[ $? -ne 0 ]]; then
		COLOR_EXIT_CODE="\[\e[0;31m\]"
	else
		COLOR_EXIT_CODE="\[\e[0;32m\]"
    fi
	
	local MY_ACTUAL_GIT_BRANCH=$(git rev-parse --abbrev-ref HEAD 2>/dev/null)
	export MY_GIT_PROMPT=""
	
    if [[ "$MY_ACTUAL_GIT_BRANCH" != "" ]]; then
		MY_GIT_PROMPT="git: $MY_ACTUAL_GIT_BRANCH"
#		PS1="\[\e[30;42m\]\u@\h \[\e[30;46m\]\w \[\e[30;43m\]$MY_GIT_PROMPT \[\e[m\] \n\[\e[0;32m\]└─\[\e[0;32m\] \t \$ ${COLOR_EXIT_CODE}▶ \[\e[m\] "		
		PS1="\[\e[30;42m\]\u@\h \[\e[30;46m\]\w \[\e[30;43m\]$MY_GIT_PROMPT \[\e[m\] \n\[\e[0;32m\]└─\[\e[0;32m\] \t \$ ${COLOR_EXIT_CODE}▶ \[\e[m\] "
	else
#		PS1="\[\e[30;42m\]\u@\h \[\e[30;46m\]\w \[\e[m\] \n\[\e[0;32m\]└─\[\e[0;32m\] \t \$ ${COLOR_EXIT_CODE}▶ \[\e[m\] "
		PS1="\[\e[30;42m\]\u@\h \[\e[30;46m\]\w \[\e[m\] \n\[\e[0;32m\]└─\[\e[0;32m\] \t \$ ${COLOR_EXIT_CODE}▶ \[\e[m\] "
	fi
}
 
PROMPT_COMMAND=my_prompt_pre_evaluate
