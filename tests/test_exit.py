import pexpect


def test_exit():
    aplikacija = pexpect.pexpect()
    try:
        aplikacija.expect("command>>: ")
        aplikacija.send("exit")
        aplikacija.expect(">> Bye")
        print "PASSED\ttest_exit"
    except:
        print "FAILED\ttest_exit"


if __name__ == "__main__":
    test_exit()
