SRC = ./*/*
JAVAC = javac


all:
	$(JAVAC) $(SRC)

run-server:
	java srv/Server

run-client:
	java srv/MainClient

mrproper:
	find . -name '*.class' -exec rm -f {} \;
