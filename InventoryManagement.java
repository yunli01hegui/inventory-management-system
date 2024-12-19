import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Scanner;

public class InventoryManagement {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/inventory_system?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL&allowPublicKeyRetrieval=true";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";

    private static Connection conn;

    public static void main(String[] args) {
        // 设置控制台输出编码为UTF-8
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.setErr(new java.io.PrintStream(System.err, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            // 手动加载 MySQL JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功！");

            // 连接数据库
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            System.out.println("数据库连接成功！");

            // 登录验证
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入用户名: ");
            String username = scanner.nextLine();
            System.out.print("请输入密码: ");
            String password = scanner.nextLine();

            if (validateLogin(username, password)) {
                String role = getUserRole(username);
                System.out.println("欢迎，" + username + " (" + role + ")!");

                if (role.equals("admin")) {
                    showAdminMenu();
                } else {
                    showUserMenu();
                }
            } else {
                System.out.println("用户名或密码错误。");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 登录验证
    private static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取用户角色
    private static String getUserRole(String username) {
        String query = "SELECT role FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 管理员菜单
    private static void showAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n管理员菜单:");
            System.out.println("1. 添加商品");
            System.out.println("2. 修改商品");
            System.out.println("3. 删除商品");
            System.out.println("4. 查看所有商品");
            System.out.println("5. 退出");

            System.out.print("请输入您的选择: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: addProduct(scanner); break;
                case 2: updateProduct(scanner); break;
                case 3: deleteProduct(scanner); break;
                case 4: viewAllProducts(); break;
                case 5: System.out.println("退出系统..."); return;
                default: System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    // 普通用户菜单
    private static void showUserMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n用户菜单:");
            System.out.println("1. 查看所有商品");
            System.out.println("2. 退出");

            System.out.print("请输入您的选择: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: viewAllProducts(); break;
                case 2: System.out.println("退出系统..."); return;
                default: System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    // 添加商品
    private static void addProduct(Scanner scanner) {
        System.out.print("请输入商品名称: ");
        String name = scanner.nextLine();
        System.out.print("请输入商品数量: ");
        int quantity = scanner.nextInt();
        System.out.print("请输入商品价格: ");
        double price = scanner.nextDouble();

        String query = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("商品添加成功！");
            } else {
                System.out.println("添加商品失败。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新商品
    private static void updateProduct(Scanner scanner) {
        System.out.print("请输入要更新的商品ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("请输入新的商品名称: ");
        String name = scanner.nextLine();
        System.out.print("请输入新的商品数量: ");
        int quantity = scanner.nextInt();
        System.out.print("请输入新的商品价格: ");
        double price = scanner.nextDouble();

        String query = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.setInt(4, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("商品更新成功！");
            } else {
                System.out.println("更新商品失败。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除商品
    private static void deleteProduct(Scanner scanner) {
        System.out.print("请输入要删除的商品ID: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("商品删除成功！");
            } else {
                System.out.println("删除商品失败。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查看所有商品
    private static void viewAllProducts() {
        String query = "SELECT * FROM products";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n商品列表:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", 名称: " + rs.getString("name")
                        + ", 数量: " + rs.getInt("quantity") + ", 价格: " + rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}