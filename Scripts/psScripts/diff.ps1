param(
    $Ethalon, 
    $Result
     )
$diff = Compare-Object -ReferenceObject (Get-Content $Ethalon) -DifferenceObject (Get-Content $Result)
if (($diff -eq $null) -and ($error[0] -eq $null))
    {Write-Host "Dump and ethalon are equal."}
else
    {$diff | foreach {
    
    if($_.SideIndicator -eq "=>") 
            { 
                $lineOperation = "Added: " 
            } 
            elseif($_.SideIndicator -eq "<=") 
            { 
                $lineOperation = "Deleted: " 
            } 
    Write-Host $lineOperation
    Write-Host $_.InputObject
    }
     Write-Host $error[0] 
    }
   
