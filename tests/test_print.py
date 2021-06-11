import pexpect
import test_add


def test_print_one():
    aplikacija = pexpect.pexpect()
    try:
        test_add.test_add_person(aplikacija, "3105940500232", "Janez Albert", "Novak", "81", "Pfeizer")
        aplikacija.expect("command>>: ")
        aplikacija.send("print")
        aplikacija.expect(">> Number of Persons: 1")
        aplikacija.expect("3105940500232\tNovak, Janez Albert\t81\tPfeizer")

        print "PASSED\ttest_print_one"
    except:
        print "FAILED\ttest_print_one"
    finally:
        aplikacija.kill()


def test_print_zero():
    aplikacija = pexpect.pexpect()
    try:
        aplikacija.expect("command>>: ")
        aplikacija.send("print")
        aplikacija.expect(">> Number of Persons: 0")
        aplikacija.expect("")
        aplikacija.expect("command>>: ")
        print "PASSED\ttest_print_zero"
    except:
        print "FAILED\ttest_print_zero"

    finally:
        aplikacija.kill()


def order_by_name(emso_dict):
    return sorted(emso_dict.items(), key=lambda x: x[1][0])


def add_persons(aplikacija):
    emso_dict = test_add.gen_person()
    for emso in emso_dict.keys():
        name_surname, age, vaccine = emso_dict[emso]
        test_add.test_add_person(aplikacija, emso, name_surname[1], name_surname[0], age, vaccine)

    return emso_dict


def check_print(aplikacija, emso_dict):
    sorted_names = order_by_name(emso_dict)
    for person in sorted_names:
        emso, person_info = person
        name_surname, age, vaccine = person_info
        aplikacija.expect("{}\t{}, {}\t{}\t{}".format(emso, name_surname[0], name_surname[1], age, vaccine))


def test_print_multiple():
    aplikacija = pexpect.pexpect()
    try:
        emso_dict = add_persons(aplikacija)
        aplikacija.expect("command>>: ")
        aplikacija.send("print")
        aplikacija.expect(">> Number of Persons: " + str(len(emso_dict)))
        check_print(aplikacija, emso_dict)
        print "PASSED\ttest_print_multiple"
    except:
        print "FAILED\ttest_print_multiple"

    finally:
        aplikacija.kill()


if __name__ == "__main__":
    test_print_one()
    test_print_zero()
    test_print_multiple()
    #emso_dict = test_add.gen_person()
    #print order_by_name(emso_dict)