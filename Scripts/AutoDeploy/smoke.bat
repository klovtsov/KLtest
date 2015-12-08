@echo off

set TPAMPath=c:\tpam
set SmokeFlag=need_to_run_smoke_tests.flag

perl autodeploy_smoke2.pl 10.30.44.191 ykcliadmin id_dsa_ykcliadmin_191.PPK 2>&1 1>deploy.log

if exist %SmokeFlag% (

	del %SmokeFlag%

	ping 1.1.1.1 -n 1 -w 180000 >NUL

	cd %TPAMPath%
	TestRunnerStandalone.cmd testrunner.config.yk.smoke.mini

)
