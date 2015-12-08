rem This script is intended to patch a custom Firefox profile (TPAM) to make Selenium RC work with recent Firefox browsers (32+)

set TPAMPath=C:\TPAM
set FirefoxProfile=%TPAMPath%\ff4s_profile
set FirefoxPrefs=%FirefoxProfile%\prefs.js

set MagicString=user_pref(\"browser.startup.homepage_override.mstone\"
set OriginalString=%MagicString%*
set NewString=%MagicString%, \"16.0.2\");

sfk filter %FirefoxPrefs% -rep "/%OriginalString%/%NewString%/" -write -yes
