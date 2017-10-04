#!/bin/bash

# para suportar macOS
if hash ggrep 2>/dev/null; then
    GREP=ggrep
else
    GREP=grep
fi
echo "teste" | $GREP -oP teste - 2>/dev/null >/dev/null
if [ "$?" -ne "0" ]; then
    echo "Você precisa ter o grep do GNU instalado para executar este script."
    exit 1
fi

if hash gsed 2>/dev/null; then
    SED=gsed
else
    SED=sed
fi

NEW_PACKAGE=$1
PROJECT_NAME=$2

CURRENT_PACKAGE="br.com.eits.starter"
CURRENT_FOLDER=$(echo $CURRENT_PACKAGE | tr . \/)

echo
while [ -z $NEW_PACKAGE ] || [[ ! "$NEW_PACKAGE" =~ ^([a-z_]+\.)*[a-z_]+$ ]]; do
    echo "Digite o novo nome de pacote raiz:"
    read NEW_PACKAGE

    if ! [[ "$NEW_PACKAGE" =~ ^([a-z_]+\.)*[a-z_]+$ ]]; then
        echo "Nome de pacote inválido."
        NEW_PACKAGE=""
    fi
done
while [ -z $PROJECT_NAME ] || [[ ! "$PROJECT_NAME" =~ ^[a-z-]+$ ]]; do
    echo "Digite o novo nome do projeto, que será usado para o banco de dados e o POM raiz:"
    read PROJECT_NAME
    if ! [[ "$PROJECT_NAME" =~ ^[a-z-]+$ ]]; then
        echo "Nome do POM inválido."
        PROJECT_NAME=""
    fi
done

POM_NAME="$PROJECT_NAME-pom"

NEW_FOLDER=$(echo $NEW_PACKAGE | tr . \/)

change_packages() {
    pushd $1
    echo "Entrando em $1"
    find . -type f -exec $SED -i "s:$CURRENT_PACKAGE:$NEW_PACKAGE:g" {} \; || exit 1
    mkdir -p src/{main,test}/java/$NEW_FOLDER
    mv -v src/main/java/$CURRENT_FOLDER/* src/main/java/$NEW_FOLDER/
    mv -v src/test/java/$CURRENT_FOLDER/* src/test/java/$NEW_FOLDER/
    rm -rf
    popd
}

cd $(dirname $BASH_SOURCE)/..

for dir in common/{domain,functional-microservices}; do
    [ -d $dir ]  && change_packages $dir
done

for dir in domain/*; do
    [ -d $dir ]  && change_packages $dir
done

for dir in microservices/{functional,infrastructure}/*; do
    [ -d $dir ]  && change_packages $dir
done

for i in common/{,domain/,functional-microservices/}pom.xml domain/pom.xml microservices/{,functional/}pom.xml pom.xml; do
    $SED -i "s:%NEW_PACKAGE%:$NEW_PACKAGE:g" $i
    $SED -i "s:%NEW_FOLDER%:$NEW_FOLDER:g" $i
    $SED -i "s:%POM_NAME%:$POM_NAME:g" $i
done

# ajustando as configs
find microservices/infrastructure/configer/src/main/resources/config/shared -name "*.yml" -exec $SED -i "s:%PROJECT_NAME%:$PROJECT_NAME:g" {} \;
find microservices/infrastructure/configer/src/main/resources/config/shared -name "*.yml" -exec $SED -i "s:%NEW_PACKAGE%:$NEW_PACKAGE:g" {} \;