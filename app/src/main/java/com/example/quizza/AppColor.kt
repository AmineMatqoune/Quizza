package com.example.quizza

class AppColor() {

    companion object{

        private var theme = "Neutral"
        private var instance: AppColor? = null

        fun getInstance(): AppColor{
            if(instance == null)
                instance = AppColor()

            return instance as AppColor
        }

        fun setTheme(newTheme: String){
            theme = newTheme
        }

        fun getBackgroundColor(): Int {
            if (theme == "Neutral")
                return R.color.blue_background
            else
                return R.color.dark_background
        }
    }

}