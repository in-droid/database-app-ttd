import pexpect
import test_print


def test_save_restore_multiple():
    aplikacija = pexpect.pexpect()
    #emso_dict = test_add.gen_person()
    try:
        emso_dict = test_print.add_persons(aplikacija)
        aplikacija.expect("command>>: ")
        aplikacija.send("print")
        aplikacija.expect(">> Number of Persons: " + str(len(emso_dict)))
        test_print.check_print(aplikacija, emso_dict)

        aplikacija.expect("command>>: ")
        aplikacija.send("save datoteka.bin")
        aplikacija.expect("OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("reset")
        aplikacija.expect("reset> Are you sure (y|n): ")
        aplikacija.send("y")
        aplikacija.expect("OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 0")
        aplikacija.expect("command>>: ")
        aplikacija.send("restore datoteka.bin")
        aplikacija.expect("OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("print")
        aplikacija.expect(">> Number of Persons: " + str(len(emso_dict)))
        test_print.check_print(aplikacija, emso_dict)

        print "PASSED\ttest_save_restore_multiple"
    except:
        print "FAILED\ttest_save_restore_multiple"


def test_save_restore_no_file_name():
    aplikacija = pexpect.pexpect()
    try:
        aplikacija.expect("command>>: ")
        aplikacija.send("restore")
        aplikacija.expect("Error: please specify a file name")
        aplikacija.expect("command>>: ")
        aplikacija.send("save")
        aplikacija.expect("Error: please specify a file name")
        print "PASSED\ttest_save_restore_no_file_name"

    except:
        print "FAILED\ttest_save_restore_no_file_name"


def test_restore_file_not_exist():
    aplikacija = pexpect.pexpect()
    try:
        aplikacija.expect("command>>: ")
        aplikacija.send("restore invalid_file.bin")
        aplikacija.expect(">> Error: IO error invalid_file_p.bin (The system cannot find the file specified)")
        print "PASSED\ttest_restore_file_not_exist"
    except:
        print "FAILED\ttest_restore_file_not_exist"


if __name__ == "__main__":
    test_save_restore_multiple()
    test_save_restore_no_file_name()
    test_restore_file_not_exist()
