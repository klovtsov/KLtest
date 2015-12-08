If $CmdLine[0] <> 2 Then Exit (1)

$command = $CmdLine[1] & " " & $CmdLine[2]

$pid = Run ($command)

If $pid == 0 Then Exit (2)
   
Sleep(2000)

If WinExists("PuTTYgen Error") Then
   WinActivate("PuTTYgen Error")
   ControlClick("PuTTYgen Error", "", "Button1")
   Sleep(1000)
   WinActivate("PuTTY Key Generator")
   WinClose("PuTTY Key Generator")
   WinWaitClose("PuTTY Key Generator", "", 10)
   Exit (3)
EndIf

WinActivate("PuTTYgen Notice")

;WinWaitActive("PuTTYgen Notice")

ControlClick("PuTTYgen Notice", "", "Button1")

WinWaitActive("PuTTY Key Generator", "", 10)

;ControlClick("PuTTY Key Generator", "", "Button6")

Send("!s")

WinWaitActive("PuTTYgen Warning", "", 10)

ControlClick("PuTTYgen Warning", "", "Button1")

WinWaitActive("Save private key as:", "", 10)

Send($CmdLine[2])

Sleep(5000)

ControlClick("Save private key as:", "", "Button1")

WinWaitActive("PuTTYgen Warning", "", 10)

ControlClick("PuTTYgen Warning", "", "Button1")

WinClose("PuTTY Key Generator")

WinWaitClose("PuTTY Key Generator", "", 10)

Exit (0)