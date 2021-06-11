import itertools

import pexpect
import random
random.seed(42)
NAME_LIST = ["Ivan", "John", "Robert", "Albert", "Janez", "Janez Albert", "Angela", "Ana", "Elena", "Borut", "Robert"]
SURNAME_LIST = ["Novak", "Oblak", "Merkel", "Lennon", "De Niro", "Einstein", "Pahor"]
VACCINES = ["AstraZeneca", "Pfeizer", "Moderna", "Johnson"]


def gen_emso(n):
    result = set()
    while len(result) < n:
        emso = ""
        for i in range(13):
            number = random.randint(0, 9)
            emso += str(number)
        result.add(emso)
    return result


def test_add_correct():
    aplikacija = pexpect.pexpect()

    try:
        test_add_person(aplikacija, "3105940500232", "Janez Albert", "Novak", "81", "Pfeizer")
        print "PASSED\ttest_add_correct"

    except:
        print "FAILED\ttest_add_correct"

    finally:
        aplikacija.kill()


def gen_person():
    result = dict()
    emso_list = gen_emso(len(NAME_LIST))
    names_surnames = zip(NAME_LIST, itertools.cycle(SURNAME_LIST))
    for i, emso in enumerate(emso_list):
        result[emso] = (names_surnames[i][1], names_surnames[i][0]), str(random.randint(0, 119)), VACCINES[i % 4]
    return result


def test_add_multiple_all_vaccines():
    aplikacija = pexpect.pexpect()
    emso_list = gen_emso(len(NAME_LIST))
    names_surnames = zip(NAME_LIST, itertools.cycle(SURNAME_LIST))
    try:
        for i, emso in enumerate(emso_list):
            test_add_person(aplikacija, emso, names_surnames[i][0], names_surnames[i][1], str(random.randint(0, 119)),
                            VACCINES[i % 4])
        print "PASSED\ttest_add_multiple_correct"
    except:
        print "FAILED\ttest_add_multiple_correct"

    finally:
        aplikacija.kill()


def test_add_person(aplikacija, emso, name, surname, age, vaccine, response="OK"):
        aplikacija.expect("command>>: ")

        aplikacija.send("add")
        aplikacija.expect("add> EMSO: ")
        aplikacija.send(emso)
        aplikacija.expect("add> NAME: ")
        aplikacija.send(name)
        aplikacija.expect("add> SURNAME: ")
        aplikacija.send(surname)
        aplikacija.expect("add> AGE: ")
        aplikacija.send(age)
        aplikacija.expect("add> VACCINE: ")
        aplikacija.send(vaccine)
        aplikacija.expect(response)


def test_add_duplicate():
    aplikacija = pexpect.pexpect()
    try:
        test_add_person(aplikacija, "3105940500232", "Janez Albert", "Novak", "81", "Pfeizer")
        test_add_person(aplikacija, "3105940500232", "Janez Albert", "Novak", "81", "Pfeizer", ">> Person already exists")
        print "PASSED\ttest_add_duplicate"
    except:
        print "FAILED\ttest_add_duplicate"


def test_add_invalid_emso():
    aplikacija = pexpect.pexpect()

    try:
        test_add_person(aplikacija, "3105940500", "Janez Albert", "Novak", "81", "Pfeizer", ">> Invalid input data")
        print "PASSED\ttest_add_invalid_emso"

    except:
        print "FAILED\ttest_add_invalid_emso"

    finally:
        aplikacija.kill()


def test_add_alphabetic_emso():
    aplikacija = pexpect.pexpect()

    try:
        test_add_person(aplikacija, "qwertyuiopasd", "Janez Albert", "Novak", "81", "Pfeizer", ">> Invalid input data")

        print "PASSED\ttest_add_alphabetic_emso"

    except:
        print "FAILED\ttest_add_alphabetic_emso"

    finally:
        aplikacija.kill()


def test_add_invalid_name_surname():
    aplikacija = pexpect.pexpect()

    try:
        test_add_person(aplikacija, "3105940500232", "Janez11 Albert", "Novak2", "81", "Pfeizer", ">> Invalid input data")

        print "PASSED\ttest_add_invalid_name_surname"

    except:
        print "FAILED\ttest_add_invalid_name_surname"

    finally:
        aplikacija.kill()


def test_add_invalid_age():
    aplikacija = pexpect.pexpect()

    try:
        test_add_person(aplikacija, "3105940500232", "Janez Albert", "Novak", "121", "Pfeizer", ">> Invalid input data")
        test_add_person(aplikacija, "3105940500232", "Janez Albert", "Novak", "-1", "Pfeizer", ">> Invalid input data")
        print "PASSED\ttest_add_invalid_age"

    except:
        print "FAILED\ttest_add_invalid_age"

    finally:
        aplikacija.kill()


def test_add_long_name_surname():

    aplikacija = pexpect.pexpect()

    try:
        aplikacija.expect("command>>: ")

        aplikacija.send("add")
        aplikacija.expect("add> EMSO: ")
        aplikacija.send("3105940500232")
        aplikacija.expect("add> NAME: ")
        aplikacija.send("Janez Albert Robert")
        aplikacija.expect(">> Invalid input data")
        aplikacija.expect("command>>: ")
        aplikacija.send("add")
        aplikacija.expect("add> EMSO: ")
        aplikacija.send("3105940500232")
        aplikacija.expect("add> NAME: ")
        aplikacija.send("Janez Albert")
        aplikacija.expect("add> SURNAME: ")
        aplikacija.send("Novak Novak Test")
        aplikacija.expect(">> Invalid input data")

        print "PASSED\ttest_add_long_name_surname"

    except:
        print "FAILED\ttest_add_long_name_surname"

    finally:
        aplikacija.kill()


def test_add_invalid_vaccine():
    aplikacija = pexpect.pexpect()

    try:
        test_add_person(aplikacija, "qwertyuiopasd", "Janez Albert", "Novak", "81", "Test", ">> Invalid input data")

        print "PASSED\ttest_add_invalid_vaccine"

    except:
        print "FAILED\ttest_add_invalid_vaccine"


def test_add_invalid_argument():
    aplikacija = pexpect.pexpect()

    try:
        aplikacija.expect("command>>: ")
        aplikacija.send("add test")
        aplikacija.expect(">> Invalid input data")

        print "PASSED\ttest_add_invalid_argument"
    except:
        print "FAILED\ttest_add_invalid_argument"


if __name__ == "__main__":
    test_add_correct()
    test_add_invalid_emso()
    test_add_alphabetic_emso()
    test_add_invalid_name_surname()
    test_add_invalid_age()
    test_add_invalid_argument()
    test_add_long_name_surname()
    test_add_invalid_vaccine()
    test_add_multiple_all_vaccines()
    test_add_duplicate()

