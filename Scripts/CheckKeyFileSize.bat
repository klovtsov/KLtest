@echo off

set filesize=%~z1

if %filesize% gtr 663 if %filesize% lss 687 (

echo "Looks like this is 1024-bit key"

goto :eof

)

if %filesize% gtr 1188 if %filesize% lss 1219 (

echo "Looks like this is 2048-bit key"

goto :eof

)
	
echo "File size is not correct"
