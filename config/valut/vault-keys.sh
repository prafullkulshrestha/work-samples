#!/bin/bash
source $(dirname $0)/vault-setup.sh
setup_vault
vault write secret/employer-api  spring.datasource.username=employer_user spring.datasource.password=testing

