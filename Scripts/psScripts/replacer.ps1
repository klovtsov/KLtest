Param(
  [string]$filePath,
  [string]$string1,
  [string]$string2
)

Get-ChildItem -Path $filePath | where { ! $_.PSIsContainer } | ForEach-Object {
   (Get-Content $_.FullName) | ForEach-Object { $_ -replace $string1, $string2
    } | Set-Content $_.FullName
  } 

  if ($Error.count -eq "0")
  {Write-Host "successful"
  }