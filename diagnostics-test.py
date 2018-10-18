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
		print("1 - test all")
		print("2 - test timestamp()")
		print("3 - test cpuBattery()")
		print("4 - test cpuCount()")
		print("5 - test availableMem()")
		print("e - Exit")
		op = input("")
		if(op == "1"):
			test_diagnostic()
		elif (op == "2"):
		elif (op == "3"):
		elif (op == "4"):
		elif (op == "5"):
		elif(op == "e"):
			print("\nBye!")
			return
		else:
			print("Invalid choice. Try again.\n")


runTests()

