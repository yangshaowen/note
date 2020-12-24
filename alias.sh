alias cde="cd /c/Users/ysw/Desktop/note"
alias push='git push origin head'
alias pushf='git push origin head -f'
alias log='git log'
alias checkout='git checkout'
alias status='git status'
alias del='git checkout . && git clean -fd'

commit(){
dateStr=`date '+%Y-%m-%d %H:%M:%S'`
git add .
git commit . -m "${dateStr}"
}