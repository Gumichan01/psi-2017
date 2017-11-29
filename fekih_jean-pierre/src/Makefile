SRC = ./*/*.java
JAVAC = javac


all:
	$(JAVAC) $(SRC)

run-server:
	java srv/Server

run-client:
	java client/MainClient

mrproper:
	find . -name '*.class' -exec rm -f {} \;
