import pexpect
import test_add


def test_reset_count():
    aplikacija = pexpect.pexpect()

    try:
        test_add.test_add_person(aplikacija, "1234567890123", "Janez", "Novak", "76", "Moderna", "OK")
        test_add.test_add_person(aplikacija, "1234567890124", "Tom", "Thomson", "72", "Moderna", "OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 2")
        aplikacija.expect("command>>: ")
        aplikacija.send("reset")
        aplikacija.expect("reset> Are you sure (y|n): ")
        aplikacija.send("b")
        aplikacija.expect("reset> Are you sure (y|n): ")
        aplikacija.send("y")
        aplikacija.expect("OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 0")
        print "PASSED\ttest_reset_count"
    except:
        print "FAILED\ttest_reset_count"


def test_reset_no():
    aplikacija = pexpect.pexpect()

    try:
        test_add.test_add_person(aplikacija, "1234567890123", "Janez", "Novak", "76", "Moderna", "OK")
        test_add.test_add_person(aplikacija, "1234567890124", "Tom", "Thomson", "72", "Moderna", "OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 2")
        aplikacija.expect("command>>: ")
        aplikacija.send("reset")
        aplikacija.expect("reset> Are you sure (y|n): ")
        aplikacija.send("n")
        aplikacija.expect("OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 2")
        print "PASSED\ttest_reset_no"
    except:
        print "FAILED\ttest_reset_no"


if __name__ == "__main__":
    test_reset_count()
    test_reset_no()