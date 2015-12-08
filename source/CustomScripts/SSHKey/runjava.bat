rem java -jar tpamjavasshkeycmd.jar authFile=id_dsa_ykapiadmin209 apiUser=ykapiadmin TPAM=10.30.44.209 1>stdout.txt 2>stderr.txt

rem java -jar tpamjavasshkeycmd.jar authFile=id_dsa_ykapiadmin209 apiUser=ykapiadmin TPAM=10.30.44.209 systemName=yktestsshkey accountName=yk5 regenerate=Y 1>stdout.txt 2>stderr.txt

java -jar tpamjavasshkeycmd.jar authFile=id_dsa_ykapiadmin209 apiUser=ykapiadmin TPAM=10.30.44.209 standardKey=id_dsa 1>stdout.txt 2>stderr.txt

