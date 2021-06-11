import pexpect
import test_add


def test_count_multiple():
    aplikacija = pexpect.pexpect()

    try:
        emso_dict = test_add.gen_person()
        for i, emso in enumerate(emso_dict.keys()):
            name_surname, age, vaccine = emso_dict[emso]
            test_add.test_add_person(aplikacija, emso, name_surname[1], name_surname[0], age, vaccine)
            aplikacija.expect("command>>: ")
            aplikacija.send("count")
            aplikacija.expect(">> No. of Persons: " + str(i + 1))
        print "PASSED\ttest_count_multiple"
    except:
        print "FAILED\ttest_count_multiple"
    finally:
        aplikacija.kill()


def test_count_zero():
    aplikacija = pexpect.pexpect()

    try:

        aplikacija.expect("command>>: ")
        aplikacija.send("count")
        aplikacija.expect(">> No. of Persons: 0")
        print "PASSED\ttest_count_zero"
    except:
        print "FAILED\ttest_count_zero"


if __name__ == "__main__":
    test_count_multiple()
    test_count_zero()