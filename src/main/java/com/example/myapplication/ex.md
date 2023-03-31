```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

    <TextView
            android:id="@+id/display_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="40sp"
            android:gravity="right"
            android:padding="24dp"
            android:textColor="#000000"/>

    <GridLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:rowCount="5"
            android:columnCount="4"
            android:layout_below="@+id/display_text"
            android:padding="8dp"
            android:alignmentMode="alignMargins"
            android:columnOrderPreserved="false">

        <Button
                android:text="7"
                android:id="@+id/button7"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="8"
                android:id="@+id/button8"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="9"
                android:id="@+id/button9"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="/"
                android:id="@+id/buttonDivide"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="4"
                android:id="@+id/button4"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="5"
                android:id="@+id/button5"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="6"
                android:id="@+id/button6"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="*"
                android:id="@+id/buttonMultiply"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="1"
                android:id="@+id/button1"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="2"
                android:id="@+id/button2"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="3"
                android:id="@+id/button3"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="-"
                android:id="@+id/buttonMinus"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="."
                android:id="@+id/buttonDecimal"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="0"
                android:id="@+id/button0"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="="
                android:id="@+id/buttonEquals"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1"
                android:layout_rowSpan="2" />

        <Button
                android:text="+"
                android:id="@+id/buttonPlus"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1" />

        <Button
                android:text="AC"
                android:id="@+id/buttonAC"
                android:layout_columnWeight="2"
                android:layout_rowWeight="1"
                android:layout_columnSpan="1" />

        <Button
                android:text="C"
                android:id="@+id/buttonC"
                android:layout_columnWeight="2"
                android:layout_rowWeight="1"
                android:layout_columnSpan="1" />

    </GridLayout>

</RelativeLayout>



```



gridlayout:

```xml
 <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            android:orientation="horizontal">

        <LinearLayout
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:orientation="vertical"
                android:padding="8dp">

            <Button
                    android:id="@+id/button1"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="1" />

            <Button
                    android:id="@+id/button4"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="4" />

            <Button
                    android:id="@+id/button7"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="7" />

            <Button
                    android:id="@+id/buttonDecimal"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="." />

        </LinearLayout>

        <LinearLayout
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:orientation="vertical"
                android:padding="8dp">

            <Button
                    android:id="@+id/button2"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="2" />

            <Button
                    android:id="@+id/button5"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="5" />

            <Button
                    android:id="@+id/button8"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="8" />

            <Button
                    android:id="@+id/button0"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="0" />

        </LinearLayout>

        <LinearLayout
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:orientation="vertical"
                android:padding="8dp">

            <Button
                    android:id="@+id/button3"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="3" />

            <Button
                    android:id="@+id/button6"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="6" />

            <Button
                    android:id="@+id/button9"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="9" />

            <Button
                    android:id="@+id/buttonEquals"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="=" />

        </LinearLayout>

        <LinearLayout
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:orientation="vertical"
                android:padding="8dp">

            <Button
                    android:id="@+id/buttonPlus"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="+" />

            <Button
                    android:id="@+id/buttonMinus"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="-" />

            <Button
                    android:id="@+id/buttonMultiply"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="*" />

            <Button
                    android:id="@+id/buttonDivide"
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="/" />

        </LinearLayout>

    </LinearLayout>
```