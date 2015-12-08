If $CmdLine[0] < 3 Or $CmdLine[0] > 4 Then Exit (1)

$command = $CmdLine[1] & "\DefenderDesktopToken.exe"

;MsgBox(0, "Command Example", "Command is: " & $command)

$pid = Run ($command)

If $pid == 0 Then Exit (2)

Sleep(1000)

WinActivate("Enter Passphrase")

WinWaitActive("Enter Passphrase")

ControlClick("Enter Passphrase", "", "Button1")

WinWaitActive("Defender Desktop Tokens")

local $line = ControlListView("Defender Desktop Tokens", "", "SysListView321", "FindItem", $CmdLine[2])

ControlListView("Defender Desktop Tokens", "", "SysListView321", "Select", $line)

Send("{ENTER}")

;ControlClick("Defender Desktop Tokens", "", "SysListView321", "Left", 2)

WinWaitActive("Enter Passphrase")

;MsgBox(0, "Command Example", "Passphrase is: " & $CmdLine[2])

Send($CmdLine[3])

ControlClick("Enter Passphrase", "", "Button2")

WinWaitActive("Defender Desktop Token Response")

;MsgBox(0, "Command Example", "Challenge is: " & $CmdLine[3])

If $CmdLine[0] == 4 Then

Send($CmdLine[4])

EndIf

ControlClick("Defender Desktop Token Response", "", "Button6")

Sleep(1000)

$response = ControlGetText("Defender Desktop Token Response", "", "Edit2")

;MsgBox(0, "Response Example", "Response is: " & $response)

ControlClick("Defender Desktop Token Response", "", "Button2")

WinClose("Defender Desktop Token Response")

WinWaitClose("Defender Desktop Token Response")

Exit $response