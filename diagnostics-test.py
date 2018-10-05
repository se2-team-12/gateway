import diagnostics


def test_diagnostic():
	print("\n------ Running test_diagnostic... ------")
	print(" ... ...\n")
	rv = diagnostics.main()
	print("----------------------------------------")
	print("--------------- Results: ---------------")
	print("\nDiagnostic returned:",rv)
	if(rv != None):
		print("!! SUCCESS. TEST_DIAGNOSTIC PASSED.")
	else:
		print("!! FAILURE. TEST_DIAGNOSTIC FAILED.")
	print("\n---------------------------------------")
	print("----------------------------------------")
   
def runTests():
	print("----------------------------------------")
	print("-------------  Test Menu:  -------------")
	print("----------------------------------------")
	op = ""
	while op != "e":
		print("Options:")
		print("1 - test_diagnostics")
		print("e - Exit")
		op = input("")
		if(op == "1"):
			test_diagnostic()
		elif(op == "e"):
			print("\nBye!")
			return
		else:
			print("Invalid choice. Try again.\n")


runTests()

