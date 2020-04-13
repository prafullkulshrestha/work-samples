#!/bin/bash

function setup_vault() {
	export VAULT_ADDR='http://localhost:8200'
	export VAULT_TOKEN='myroot'
	SECRET_DESCRIPTION=keyspring
	if vault secrets list | grep -q $SECRET_DESCRIPTION ; then
		echo "vault already set up"
	else
		echo "setting up vault"
		vault secrets disable secret
		vault secrets enable -version=1 -path=secret -description=$SECRET_DESCRIPTION kv
	fi
}
