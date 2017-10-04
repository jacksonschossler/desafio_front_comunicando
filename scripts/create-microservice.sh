#!/usr/bin/env bash

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

NAME=$1

while [ -z $NAME ] || [[ ! "$NAME" =~ ^[a-z]+$ ]]; do
    echo "Digite o nome do novo microserviço:"
    read NAME

    if ! [[ "$NAME" =~ ^[a-z]+$ ]]; then
        echo "Nome inválido."
        NAME=""
    fi
done

cd $(dirname $BASH_SOURCE)/..

ROOT_PACKAGE=$(find $(dirname $BASH_SOURCE)/../common/functional-microservices/src/main/java -name CommonConfiguration.java -exec $GREP -oP '(?<=^package )([a-z_]+\.?)*[a-z_](?=.common;\s*$)' {} \;)
ROOT_FOLDER=$(echo $ROOT_PACKAGE | tr . \/)

echo "Lendo versão atual do pom.xml..."
VERSION=$(mvn -q \
    -Dexec.executable="echo" \
    -Dexec.args='${project.version}' \
    --non-recursive \
    org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
echo "Estamos na versão $VERSION."

# essa função é bem similar à que está em change-root-package.sh,
# porém aqui só precisamos mudar de estrutura de pasta, pois todos
# os packages no java já estão com placeholders.
resolve_placeholders() {
    echo "Movendo arquivos..."
    echo $ROOT_FOLDER
    pushd $1
    mkdir -p src/{main,test}/java/$ROOT_FOLDER
    mv -v src/main/java/br/com/eits/starter/* src/main/java/$ROOT_FOLDER
    rm -rf src/main/java/br/com/eits/starter
    mv -v src/main/test/br/com/eits/starter/* src/test/java/$ROOT_FOLDER
    rm -rf src/test/java/br/com/eits/starter

    # movendo as pastas de template
    find . -name template -type d -exec bash -c "mv -v \"\$0\" \"\${0//template/$NAME}\"" {} \;
    # renomeando os arquivos com %TEMPLATE% no nome
    find . -name "*%TEMPLATE%*" -type f -exec bash -c "mv -v \"\$0\" \"\${0//%TEMPLATE%/$NAME}\"" {} \;
    # substituindo %ROOT_PACKAGE%
    find . -type f -exec $SED -i "s:%ROOT_PACKAGE%:$ROOT_PACKAGE:g" {} \; || exit 1
    # substituindo %TEMPLATE%
    find . -type f -exec $SED -i "s:%TEMPLATE%:$NAME:g" {} \; || exit 1
    # substituindo %VERSION%
    find . -type f -exec $SED -i "s:%VERSION%:$VERSION:g" {} \; || exit 1

    popd
}

cp -av scripts/templates/domain domain
mv domain/domain domain/$NAME
resolve_placeholders domain/$NAME
cp -av scripts/templates/functional microservices/functional
mv microservices/functional/functional microservices/functional/$NAME
resolve_placeholders microservices/functional/$NAME

# adicionando nos pom.xml
awk "!found && /<\/modules>/ { print \"\t\t<module>$NAME</module>\"; found=1 } 1" domain/pom.xml > tmp && mv tmp domain/pom.xml
awk "!found && /<\/modules>/ { print \"\t\t<module>$NAME</module>\"; found=1 } 1" microservices/functional/pom.xml > tmp && mv tmp microservices/functional/pom.xml

echo "Microserviço $NAME gerado."