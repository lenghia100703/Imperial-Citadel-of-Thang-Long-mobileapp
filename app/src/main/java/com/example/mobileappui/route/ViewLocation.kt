package com.example.mobileappui.route

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.location.LocationDto
import com.example.mobileappui.dtos.post.PostDto
import com.example.mobileappui.retrofit.ApiClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewLocation() : Fragment() {
    private lateinit var map: WebView
    private lateinit var buttonBack: Button
    private lateinit var buttonGuide: Button
    private lateinit var buttonLocation: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var image: ImageView
    private lateinit var desc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_location, container, false)
        desc = view.findViewById(R.id.desc)
        map = view.findViewById(R.id.map)
        button1 = view.findViewById(R.id.btnRoute1)
        button2 = view.findViewById(R.id.btnRoute2)
        button3 = view.findViewById(R.id.btnRoute3)
        button4 = view.findViewById(R.id.btnRoute4)
        val locationList: List<Button> = listOf(button1, button2, button3, button4)
        map.settings.setJavaScriptEnabled(true)
        map.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }
        buttonBack = view.findViewById(R.id.back_btn)
        buttonBack.setOnClickListener {
            replaceFragment(Home())
        }
        buttonGuide = view.findViewById(R.id.btnGuide)
        buttonGuide.setOnClickListener {
            map.loadUrl("https://www.google.com/maps/dir/21.0375886,105.7817815/Ho%C3%A0ng+Th%C3%A0nh+Th%C4%83ng+Long,+Ho%C3%A0ng+Di%E1%BB%87u,+Qu%C3%A1n+Th%C3%A1nh,+Ba+%C4%90%C3%ACnh,+Hanoi/@21.0305011,105.7903383,14z/data=!3m1!4b1!4m10!4m9!1m1!4e1!1m5!1m1!1s0x3135aba3381d7c49:0xb521a7d98f582937!2m2!1d105.8402038!2d21.0352298!3e2?entry=ttu")
            desc.visibility = View.INVISIBLE
            for (i in locationList.indices) {
                locationList[i].setBackgroundColor(Color.parseColor("#AAA9A9"))
            }
        }
        buttonLocation = view.findViewById(R.id.btnPosition)
        buttonLocation.setOnClickListener {
            map.loadUrl("http://www.google.com/maps?q=21.038011910623236,105.78317566166638")
            desc.visibility = View.INVISIBLE
            for (i in locationList.indices) {
                locationList[i].setBackgroundColor(Color.parseColor("#AAA9A9"))
            }
        }

        button1.setOnClickListener {
            map.loadUrl("http://www.google.com/maps?q=21.033842880088567, 105.83918305247721")
            desc.visibility = View.VISIBLE
            for (i in locationList.indices) {
                if (i == 0) {
                    locationList[i].setBackgroundColor(Color.parseColor("#c99c34"))
                }
                else {
                    locationList[i].setBackgroundColor(Color.parseColor("#AAA9A9"))
                }
            }
            desc.text = "  Năm 1009, tương truyền khi vua Lý Công Uẩn rời kinh đô Hoa Lư đến đất Đại La thì thấy rồng bay lên nên gọi tên kinh đô mới là Thăng Long, hay \"rồng bay lên\" theo nghĩa Hán Việt. Ngày nay tên Thăng Long còn dùng trong văn chương, trong những cụm từ như \"Thăng Long ngàn năm văn vật\"... Năm 2010 là kỷ niệm Đại lễ 1000 năm Thăng Long - Hà Nội.\n" +
                    "\n" +
                    "Năm 1243, nhà Trần tôn tạo sửa đổi và gọi Thăng Long là Long Phượng. Cuối thời Trần, Hồ Quý Ly cho đặt tên là Đông Đô."
        }

        button2.setOnClickListener {
            for (i in locationList.indices) {
                if (i == 1) {
                    locationList[i].setBackgroundColor(Color.parseColor("#c99c34"))
                }
                else {
                    locationList[i].setBackgroundColor(Color.parseColor("#AAA9A9"))
                }
            }
            desc.visibility = View.VISIBLE
            map.loadUrl("http://www.google.com/maps?q=21.03465628319346, 105.84019255762902")
            desc.text = ("  Hà Nội là thủ đô, thành phố trực thuộc trung ương và là một trong hai đô thị loại đặc biệt của nước Cộng hòa xã hội chủ nghĩa Việt Nam. Đây là thành phố lớn nhất (về mặt diện tích) Việt Nam, có vị trí là trung tâm chính trị, một trong hai trung tâm kinh tế, văn hóa, giáo dục quan trọng tại Việt Nam. Hà Nội nằm về phía tây bắc của trung tâm vùng đồng bằng châu thổ sông Hồng, với địa hình bao gồm vùng đồng bằng trung tâm và vùng đồi núi ở phía bắc và phía tây thành phố. Với diện tích 3.359,82 km²,[2] và dân số 8,4 triệu người,Hà Nội là thành phố trực thuộc trung ương có diện tích lớn nhất Việt Nam, đồng thời cũng là thành phố đông dân thứ hai và có mật độ dân số cao thứ hai trong 63 đơn vị hành chính cấp tỉnh của Việt Nam, nhưng phân bố dân số không đồng đều. Hà Nội có 30 đơn vị hành chính cấp huyện, gồm 12 quận, 17 huyện và 01 thị xã.")
        }

        button3.setOnClickListener {
            for (i in locationList.indices) {
                if (i == 2) {
                    locationList[i].setBackgroundColor(Color.parseColor("#c99c34"))
                }
                else {
                    locationList[i].setBackgroundColor(Color.parseColor("#AAA9A9"))
                }
            }
            desc.visibility = View.VISIBLE
            map.loadUrl("http://www.google.com/maps?q=21.036640288265282, 105.83917549011132")
            desc.text = ("  Khi cuộc Chiến tranh Việt Nam leo thang, Hà Nội phải hứng chịu những cuộc tấn công trực tiếp từ Hoa Kỳ. Riêng trong chiến dịch Linebacker II năm 1972, trong khoảng 2.200 người dân bị thiệt mạng ở miền Bắc, số nạn nhân ở Hà Nội được thống kê là 1.318 người. Nhiều cơ quan, trường học phải sơ tán tới các tỉnh lân cận.")
        }

        button4.setOnClickListener {
            for (i in locationList.indices) {
                if (i == 3) {
                    locationList[i].setBackgroundColor(Color.parseColor("#c99c34"))
                }
                else {
                    locationList[i].setBackgroundColor(Color.parseColor("#AAA9A9"))
                }
            }
            desc.visibility = View.VISIBLE
            map.loadUrl("http://www.google.com/maps?q=21.036783005627118, 105.8404342855841")
            desc.text = ("  Triều đại Tây Sơn sụp đổ sau một thời gian ngắn ngủi, Gia Long lên ngôi năm 1802 lấy kinh đô ở Phú Xuân, bắt đầu nhà Nguyễn. Năm 1805, Gia Long cho phá tòa thành cũ của Thăng Long, xây dựng thành mới mà dấu vết còn lại tới ngày nay, bao bọc bởi các con đường Phan Đình Phùng, Hùng Vương, Trần Phú và Phùng Hưng. Năm 1831, trong cuộc cải cách hành chính của Minh Mạng, toàn quốc được chia thành 29 tỉnh, Thăng Long thuộc tỉnh \"Hà Nội\". Với hàm nghĩa \"nằm trong sông\", tỉnh Hà Nội khi đó gồm 4 phủ, 15 huyện, nằm giữa sông Hồng và Sông Đáy. Tỉnh Hà Nội gồm thành Thăng Long, phủ Hoài Đức của trấn Sơn Tây, và ba phủ Ứng Hoà, Thường Tín, Lý Nhân của trấn Sơn Nam; trong đó Phủ Hoài Đức gồm 3 huyện: Thọ Xương, Vĩnh Thuận, Từ Liêm; phủ Thường Tín gồm 3 huyện: Thượng Phúc, Thanh Trì, Phú Xuyên; phủ Ứng Hoà gồm 4 huyện: Sơn Minh (nay là Ứng Hòa), Hoài An (nay là phía nam Ứng Hòa và một phần Mỹ Đức), Chương Đức (nay là Chương Mỹ – Thanh Oai); và phủ Lý Nhân gồm 5 huyện: Nam Xang (nay là Lý Nhân), Kim Bảng, Duy Tiên, Thanh Liêm, Bình Lục. Hà Nội có tên gọi bắt đầu từ đây.")
        }
        return view
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    
}