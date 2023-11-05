driver:
	javac --release 11 -encoding utf-8 domini/*/*.java presentacio/*.java persistencia/*.java

tests:
	javac -cp ../lib/junit-4.13.1.jar:../lib/hamcrest-core-1.3.jar --release 11 domini/*/*.java test/domini/*/*.java

executa:
	java presentacio.mainTest

clean:
	@rm -f domini/Clasees/*.class
	@rm -f domini/controladors/*.class
	@rm -f domini/Drivers/*.class
	@rm -f presentacio/*.class
	@rm -f persistencia/*.class