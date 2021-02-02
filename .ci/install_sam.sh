#!/bin/bash

if sam -v &> /dev/null
then
    echo "sam is already installed"
    exit 0
fi

/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
test -d /home/linuxbrew/.linuxbrew && eval $(/home/linuxbrew/.linuxbrew/bin/brew shellenv)
brew tap aws/tap
brew install aws-sam-cli