<#
   This script retrieves PasswordId from a result of ForceReset command applied 
   to manual managed account.
   It expects incoming sting like to:
      Password PasswordId (as example "545838 9cJeC9Gu")
#>
param(
    [string]$ForceResetResult
)
#$ForceResetResult -match '\s+([0-9]{6,})$'|Out-Null
$ForceResetResult -match '^([0-9]{5,})'|Out-Null
$matches[1]