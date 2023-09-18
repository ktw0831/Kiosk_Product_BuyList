package a_010_java_after2;

import java.sql.*;
import java.util.Scanner;

class Product_BuyList {
    public int ord_no;
    public int ord_count;
    public int ord_pdt_id;
    public int ord_buying_count;
    public int ord_pdt_unit_price;
    public int ord_price;
    public String method;
	public int pdt_id_name;

    void printScore() {
        System.out.printf("%5d    %5d     %2d  %1d    %1d   %1d %n",
                ord_no, ord_count, ord_pdt_id, ord_buying_count, ord_pdt_unit_price, ord_price);
    }
}

public class Kiosk_Product_BuyList {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int total = 0;

        do {
            System.out.println("주문번호를 입력하세요 (전체 주문 리스트 출력: 1, 종료: 9): ");
            int in = input.nextInt();

            if (in == 1) {
            	Kiosk_Product_BuyTotal.main(args);
                continue;
            } else if (in == 9) {
                System.out.println("종료합니다.");
                Kiosk_MainMenu.main(args);
                break;
            }

            Connection conn = null;
            PreparedStatement pstmt = null;
            String sql;

            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String id = "system";
            String pw = "1234";

            try {
                Class.forName("oracle.jdbc.OracleDriver");
                System.out.println("클래스 로딩 성공");
                conn = DriverManager.getConnection(url, id, pw);
                System.out.println("DB 접속");

                sql = "select * from tbl_order_list where ord_no=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, in);

                ResultSet rs = pstmt.executeQuery();

                System.out.println("=========================================");
                System.out.println("주문번호    순번  상품코드 수량 단가  금액");
                System.out.println("=========================================");

                Product_BuyList p = new Product_BuyList();
                while (rs.next()) {
                    p.ord_no = rs.getInt("ord_no");
                    p.ord_count = rs.getInt("ord_count");
                    p.ord_pdt_id = rs.getInt("ord_pdt_id");
                    p.ord_buying_count = rs.getInt("ord_buying_count");
                    p.ord_pdt_unit_price = rs.getInt("ord_pdt_unit_price");
                    p.ord_price = rs.getInt("ord_price");

                    p.printScore();
                    total += p.ord_price;
                }
                System.out.println("======================================");
                System.out.println("***매출합계: " + total);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
	