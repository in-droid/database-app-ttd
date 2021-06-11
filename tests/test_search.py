import pexpect
import test_add


def test_search_ok():
    aplikacija = pexpect.pexpect()
    emso_dict = test_add.gen_person()
    try:
        for emso in emso_dict.keys():
            # print emso_dict[emso]
            name_surname, age, vaccine = emso_dict[emso]
            # print name_surname[0], name_surname[1], age, vaccine
            test_add.test_add_person(aplikacija, emso, name_surname[1], name_surname[0], age, vaccine)

        for emso in emso_dict.keys():
            aplikacija.expect("command>>: ")
            aplikacija.send("search " + emso)
            name_surname, age, vaccine = emso_dict[emso]
            aplikacija.expect("{}\t{}, {}\t{}\t{}".format(emso, name_surname[0], name_surname[1], age, vaccine))

        for emso in emso_dict.keys():
            aplikacija.expect("command>>: ")
            aplikacija.send("search")
            aplikacija.expect("search> NAME: ")
            name_surname, age, vaccine = emso_dict[emso]
            aplikacija.send(name_surname[1])
            aplikacija.expect("search> SURNAME: ")
            aplikacija.send(name_surname[0])
            aplikacija.expect("{}\t{}, {}\t{}\t{}".format(emso, name_surname[0], name_surname[1], age, vaccine))

        print "PASSED\ttest_search_ok"
    except:
        print "FAILED\ttest_search_ok"
    finally:
        aplikacija.kill()


def test_search_person_not_exist():
    aplikacija = pexpect.pexpect()
    try:
        test_add.test_add_person(aplikacija, "3105940500232", "Janez Albert", "Novak", "81", "Pfeizer")
        aplikacija.expect("command>>: ")
        aplikacija.send("search 1234567890123")
        aplikacija.expect(">> Person does not exist")
        print "PASSED\ttest_search_person_not_exist"
    except:
        print "FAILED\ttest_search_person_not_exist"
    finally:
        aplikacija.kill()


def test_search_invalid_data():
    aplikacija = pexpect.pexpect()
    try:
        aplikacija.expect("command>>: ")
        aplikacija.send("search 123 132")
        aplikacija.expect(">> Invalid input data")
        aplikacija.expect("command>>: ")
        aplikacija.send("search 12345678901a")
        aplikacija.expect(">> Invalid input data")
        aplikacija.expect("command>>: ")
        aplikacija.send("search")
        aplikacija.expect("search> NAME: ")
        aplikacija.send("!van")
        aplikacija.expect("search> SURNAME: ")
        aplikacija.send("Nikolov")
        aplikacija.expect(">> Invalid input data")
        aplikacija.expect("command>>: ")
        aplikacija.send("search")
        aplikacija.expect("search> NAME: ")
        aplikacija.send("Ivan")
        aplikacija.expect("search> SURNAME: ")
        aplikacija.send("Nikolov1")
        aplikacija.expect(">> Invalid input data")
        print "PASSED\ttest_search_invalid_data"
    except:
        print "FAILED\ttest_search_invalid_data"
    finally:
        aplikacija.kill()


if __name__ == "__main__":
    test_search_ok()
    test_search_person_not_exist()
    test_search_invalid_data()