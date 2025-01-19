const { exec } = require('child_process');
const path = require('path');

// Set the paths to your Java source, compiled output, and dependencies (JAR files) in the backend folder
const srcPath = path.join(__dirname, '..', 'backend', 'src');  // Java source files are in backend/src
const binPath = path.join(__dirname, '..', 'backend', 'bin');  // Compiled output goes in backend/bin
const libsPath = path.join(__dirname, '..', 'backend', 'libs');  // Dependencies (JAR files) are in backend/libs

// Wrap paths in quotes to handle spaces in folder names
const compileCommand = `javac -d "${binPath}" "${srcPath}/Main.java"`;

// Execute the compile command
exec(compileCommand, (compileError, compileStdout, compileStderr) => {
  if (compileError) {
    console.error(`Error compiling Java code: ${compileError.message}`);
    return;
  }
  if (compileStderr) {
    console.error(`Compile stderr: ${compileStderr}`);
    return;
  }

  console.log(`Compile stdout: ${compileStdout}`);
  console.log('Java code compiled successfully!');

  // Run the Java program with the appropriate classpath
  const runCommand = process.platform === 'win32' 
    ? `java -cp "${binPath};${libsPath}\\*" Main`  // Windows uses ';' and '\\'
    : `java -cp "${binPath}:${libsPath}/*" Main`;  // UNIX-based systems use ':' and '/'

  exec(runCommand, (runError, runStdout, runStderr) => {
    if (runError) {
      console.error(`Error running Java program: ${runError.message}`);
      return;
    }
    if (runStderr) {
      console.error(`Run stderr: ${runStderr}`);
      return;
    }

    console.log(`Run stdout: ${runStdout}`);
  });
});