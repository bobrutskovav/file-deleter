$env:PATH = "D:/soft/filedeleter;$($env:PATH)"



$mypath = $PSScriptRoot
Write-Output "Path of the script : $mypath"
Push-Location D:/soft/filedeleter

& "file-deleter-native-1.0" "-d $mypath"
