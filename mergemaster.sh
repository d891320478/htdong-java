git pull
branch=$(git branch | sed -n -e 's/^\* \(.*\)/\1/p')
git checkout master
git pull -p
git checkout $branch
git merge master

