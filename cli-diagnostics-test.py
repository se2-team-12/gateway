import battery
import cpuCount
import availableMem
import timestamp
import osTest
import freeMem


def format_test(diagnostic, d_type):
    print("----------------------------------------")
    print("--------------- Results: ---------------")
    print("\nDiagnostic returned:", diagnostic)
    if diagnostic is None:
        print("!! FAILURE. TEST_DIAGNOSTIC FAILED: Diagnostic not returned.")
    elif type(diagnostic) is not d_type:
        print("!! FAILURE. TEST_DIAGNOSTIC FAILED: Incorrect type returned.")
    else:
        print("!! SUCCESS. TEST_DIAGNOSTIC PASSED.")
    print("\n--------------------------------------")
    print("--------------------------------------")

def test_osTest():
    print("\ntest_osTest()")
    rv = osTest.os
    rv_type = str
    format_test(rv, rv_type)


def test_timestamp():
    print("\ntest_timestamp()")
    rv = timestamp.timestamp
    rv_type = float
    format_test(rv, rv_type)


def test_memory():
    print("\ntest_memory()")
    rv1 = availableMem.available_mem
    rv2 = freeMem.free_mem
    rv_type = int
    print("\navailableMem test")
    format_test(rv1, rv_type)
    print("\nfreeMem test")
    format_test(rv2, rv_type)


def test_cpuCount():
    print("\ntest_cpuCount()")
    rv = cpuCount.cpu_count
    rv_type = int
    format_test(rv, rv_type)


def test_battery():
    print("\ntest_battery()")
    rv = battery.battery
    rv_type = int
    format_test(rv, rv_type)


def test_all():
    print("\nTesting ALL...")
    test_battery()
    test_cpuCount()
    test_memory()
    test_timestamp()


def menu():
    print("\n----------------------------------------")
    print("-------------  Test Menu:  -------------")
    print("----------------------------------------")
    op = ""
    while op != "e":
        print("\nOptions:")
        print("1 - test all")
        print("2 - test battery")
        print("3 - test cpuCount")
        print("4 - test memory")
        print("5 - test timestamp")
        print("6 - test osTest")
        print("e - Exit")
        op = input("")
        if op == "1":
            test_all()
        elif op == "2":
            test_battery()
        elif op == "3":
            test_cpuCount()
        elif op == "4":
            test_memory()
        elif op == "5":
            test_timestamp()
        elif op == "6":
            test_osTest()
        elif op == "e":
            print("\nBye!")
            return
        else:
            print("Invalid choice. Try again.\n")


menu()
