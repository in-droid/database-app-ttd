import pexpect
from test_add import test_add_person


def test_remove_emso():
    aplikacija = pexpect.pexpect()
    try:
        test_add_person(aplikacija, "3105940500232", "Janez", "Novak", "25", "Moderna")
        test_add_person(aplikacija, "0405940500232", "Albert", "Baker", "75", "AstraZeneca")
        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 2")
        aplikacija.expect("command>>: ")
        aplikacija.send("remove 3105940500232")
        aplikacija.expect("OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("print")
        aplikacija.expect(">> Number of Persons: 1")
        aplikacija.expect("0405940500232\tBaker, Albert\t75\tAstraZeneca")
        aplikacija.expect("command>>: ")
        aplikacija.send("remove 0405940500232")
        aplikacija.expect("OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("remove 0405940500232")
        aplikacija.expect(">> Person does not exist")
        print "PASSED\ttest_remove_emso_ok"
    except:
        print "FAILED\ttest_remove_emso_ok"
    finally:
        aplikacija.kill()


def remove_person(aplikacija, name, surname, response="OK"):
    aplikacija.expect("command>>: ")
    aplikacija.send("remove")
    aplikacija.expect("remove> NAME: ")
    aplikacija.send(name)
    aplikacija.expect("remove> SURNAME: ")
    aplikacija.send(surname)
    aplikacija.expect(response)


def test_remove_name_ok():
    aplikacija = pexpect.pexpect()
    try:
        test_add_person(aplikacija, "3105940500232", "Janez", "Novak", "25", "Moderna")
        test_add_person(aplikacija, "0405940500232", "Albert", "Baker", "75", "AstraZeneca")
        remove_person(aplikacija, "Janez", "Novak", "OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 1")
        remove_person(aplikacija, "Albert", "Baker", "OK")
        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 0")
        print "PASSED\ttest_remove_name_ok"

    except:
        print "FAILED\ttest_remove_name_ok"


def test_remove_not_exist():
    aplikacija = pexpect.pexpect()
    try:
        remove_person(aplikacija, "Janez", "Novak", ">> Person does not exist")
        print "PASSED\ttest_remove_not_exist"
    except:
        print "PASSED\ttest_remove_not_exist"


def test_remove_invalid_data():
    aplikacija = pexpect.pexpect()
    try:
        remove_person(aplikacija, "Name1", "Surname", ">> Invalid input data")
        remove_person(aplikacija, "Name", "Surname1", ">> Invalid input data")
        aplikacija.expect("command>>: ")
        aplikacija.send("remove")
        aplikacija.expect("remove> NAME: ")
        aplikacija.send("Name Name Name")
        aplikacija.expect(">> Invalid input data")
        aplikacija.expect("command>>: ")
        aplikacija.send("remove")
        aplikacija.expect("remove> NAME: ")
        aplikacija.send("Name Name")
        aplikacija.expect("remove> SURNAME: ")
        aplikacija.send("Surname surname surname")
        aplikacija.expect(">> Invalid input data")

        print "PASSED\ttest_remove_invalid_data"
    except:
        print "FAILED\ttest_remove_invalid_data"


if __name__ == "__main__":
    test_remove_emso()
    test_remove_name_ok()
    test_remove_invalid_data()
    test_remove_not_exist()
