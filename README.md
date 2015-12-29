[![Build Status](https://travis-ci.org/his-eg/plugfy.svg)](https://travis-ci.org/his-eg/plugfy)

Plugfy
-

Plugfy verifies whether a .jar file can be loaded.


Checks
--
Plugfy checks for required classes, fields, methods and supports dependencies
 checks on Spring beans.


For invoked methods plugfy checks the signature for compatibility. This is 
especially helpful because there are source code compatibilite changes
that will break binary compatibility in java. For example replace the
type of a method parameter with a super type and the code will compile
just fine. But pre compiled code will look for a method signature with the
original parameter type.


Usage
--

Plugfy is currently a proof of concept. We are working on building an easy
to use library with proper documentation. For now, have a look at 
src / java / net / sf / plugfy / verifier / Main.java to get started.

Legal
--
Plugfy is released under the Apache License 2.0. See license.txt

Please report ideas and bugs to http://plugfy.sourceforge.net