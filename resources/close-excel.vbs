Rem Author: Nagaraj Venkobasa
Rem Description: Closes particular excel file if its opened
Rem Input Value: File name with path
On Error Resume Next
	Dim objXLApp
	Set objXLApp = Eval("GetObject(, ""Excel.Application"")")
	iWBCnt = objXLApp.WorkBooks.Count
	For iCnt = 1 to iWBCnt
		If Instr(WScript.Arguments(0), objXLApp.WorkBooks(iCnt).Name) > 0 Then
			objXLApp.WorkBooks(iCnt).Close(True)
			Set objXLApp = Nothing
			Exit For
		End If
	Next
On Error Goto 0