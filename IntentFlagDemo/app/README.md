

给intent设置这两种flag会回到指定的Activity 并且会调用onNewIntent方法

    public void toA(View view) {
        Intent intent = new Intent(this,AActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //只设置这一种flag会把指定Activity销毁 并且重建
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

