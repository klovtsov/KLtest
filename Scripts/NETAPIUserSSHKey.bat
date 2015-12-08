rem tpamapinetusesshkey.exe keyFile=id_dsa_ykapiadmin209 apiUser=ykapiadmin tpamAddress=10.30.44.209 userName=value keyType=value passphrase=value regenerate=Y

Scripts\tpamapinetusersshkey.exe keyFile="Scripts\Keys\%~2" apiUser=%3 tpamAddress=%1 userName=%4 keyType=%5 regenerate=%6 > cliout.tmp
