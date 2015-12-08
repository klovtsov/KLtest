try {

	dirName = args[0]
	fileName = args[1]

	h = new libCommon()
	h.check_and_delete_file(dirName, fileName)

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

println("Finished.");





