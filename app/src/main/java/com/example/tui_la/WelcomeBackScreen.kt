

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_welcome_welcomeback)

        /*TODO: add code to determine if new or returning user. Depending on answer, adjust WelcomeBackText to say "Welcome".
           Also adjust the usernameText to the correct username.*/
        /* Tentative code?
        private val userRef = db.collection("user").document("userID")
        userRef.get()
          .addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    */

        val progressBar = findViewById<ProgressBar>(R.id.welcomeScreenProgressBar)
        Thread(Runnable {
            while (progressBarStatus < 100){
                try{
                    loadInt += 25
                    Thread.sleep(1000)
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
                this.progressBarStatus = loadInt
                progressBar.progress = progressBarStatus
            }
        }).start()
        launchHowFeelingScreen()
    }
    private fun launchHowFeelingScreen() {
        val intent = Intent(this, GuidedMeditationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.finish()
    }

}