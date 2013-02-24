Plugfy verifies whether a .jar file can be loaded.

Plugfy checks for required classes, fields, methods and supports dependencies
 checks on Spring beans.


For invoked methods plugfy checks the signature for compatibility. This is 
especially helpful because there are source code compatibilite changes
that will break binary compatibility in java. For example replace the
type of a method parameter with a super type and the code will compile
just fine. But pre compiled code will look for a method signature with the
original parmaeter type.


Plugfy is currently a proof of concept. We are working on building an easy
to use library with proper documentation.