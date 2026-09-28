package com.example.indian_shopping_system.config;

import com.example.indian_shopping_system.model.Category;
import com.example.indian_shopping_system.model.Product;
import com.example.indian_shopping_system.model.Role;
import com.example.indian_shopping_system.model.User;
import com.example.indian_shopping_system.repository.CategoryRepository;
import com.example.indian_shopping_system.repository.ProductRepository;
import com.example.indian_shopping_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final com.example.indian_shopping_system.repository.OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 1. Seed 3 Admins & Customers with complete registration profiles
        User admin1 = null;
        User customer1 = null;
        User customer2 = null;

        if (userRepository.count() == 0) {
            // 4 Dedicated Admin Accounts (Confidential, not listed on public website)
            User adminVikas = new User(
                null,
                "vikas",
                "vikas@indianshopping.in",
                passwordEncoder.encode("admin123"),
                Role.ADMIN,
                "Vikas Saroj (Admin)",
                "+91 98111 22001",
                "Headquarters, Sector 62, Noida, Uttar Pradesh 201309"
            );

            User adminAshika = new User(
                null,
                "ashika",
                "ashika@indianshopping.in",
                passwordEncoder.encode("admin123"),
                Role.ADMIN,
                "Ashika Sharma (Admin)",
                "+91 98111 22002",
                "Logistics Hub, Whitefield, Bengaluru, Karnataka 560066"
            );

            User adminSamrudhi = new User(
                null,
                "samrudhi",
                "samrudhi@indianshopping.in",
                passwordEncoder.encode("admin123"),
                Role.ADMIN,
                "Samrudhi Patel (Admin)",
                "+91 98111 22003",
                "Quality Fulfillment, Okhla Phase III, New Delhi 110020"
            );

            User adminSarvesh = new User(
                null,
                "sarvesh",
                "sarvesh@indianshopping.in",
                passwordEncoder.encode("admin123"),
                Role.ADMIN,
                "Sarvesh Verma (Admin)",
                "+91 98111 22004",
                "Central Tech Ops, Andheri East, Mumbai, Maharashtra 400069"
            );

            // Single Customer Account
            User singleCustomer = new User(
                null,
                "customer",
                "customer@indianshopping.in",
                passwordEncoder.encode("user123"),
                Role.USER,
                "Priya Sharma (Verified Customer)",
                "+91 98765 43210",
                "Flat 302, Royal Palms, Bandra West, Mumbai, Maharashtra 400050"
            );

            userRepository.saveAll(List.of(adminVikas, adminAshika, adminSamrudhi, adminSarvesh, singleCustomer));
            System.out.println(">>> 4 Admins (vikas, ashika, samrudhi, sarvesh) and 1 Customer (customer) initialized successfully!");
        }

        // 2. Seed Categories and Comprehensive Indian Catalog
        if (categoryRepository.count() == 0) {
            Category ethnic = categoryRepository.save(new Category(null, "Indian Ethnic Wear", "Traditional Sarees, Kurtas, Lehengas & Sherwanis"));
            Category spices = categoryRepository.save(new Category(null, "Spices & Organic Food", "Authentic Indian spices, teas, and organic farm produce"));
            Category handicrafts = categoryRepository.save(new Category(null, "Handicrafts & Decor", "Brass idols, wooden carvings, and Rajasthani pottery"));
            Category sweets = categoryRepository.save(new Category(null, "Indian Sweets & Mithai", "Fresh traditional sweets like Kaju Katli, Rasgulla, and Laddoos"));
            Category electronics = categoryRepository.save(new Category(null, "Electronics & Smart Gadgets", "Smartphones, earphones, audio and wearable devices"));

            // 3. Seed Products (5 per category)
            productRepository.saveAll(List.of(
                // Category 1: Indian Ethnic Wear
                new Product(
                    null,
                    "Banarasi Katan Silk Saree",
                    "Pure handwoven zari work Banarasi silk saree with royal golden borders and matching blouse piece.",
                    new BigDecimal("4999.00"),
                    "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=500",
                    25,
                    ethnic
                ),
                new Product(
                    null,
                    "Lucknowi Chikankari Men's Kurta",
                    "Hand-embroidered pure cotton festive kurta in pristine royal white.",
                    new BigDecimal("1899.00"),
                    "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=500",
                    40,
                    ethnic
                ),
                new Product(
                    null,
                    "Kanchipuram Temple Border Silk Saree",
                    "Authentic South Indian Kanchipuram silk with rich pallu and contrasting borders.",
                    new BigDecimal("6499.00"),
                    "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?w=500",
                    18,
                    ethnic
                ),
                new Product(
                    null,
                    "Rajasthani Jaipuri Bandhani Dupatta",
                    "Traditional tie & dye pure georgette dupatta with delicate gotta patti border.",
                    new BigDecimal("899.00"),
                    "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500",
                    50,
                    ethnic
                ),
                new Product(
                    null,
                    "Royal Jodhpuri Bandhgala Suit for Men",
                    "Tailored slim-fit Jodhpuri suit crafted in fine jacquard blend with metallic buttons.",
                    new BigDecimal("5999.00"),
                    "https://images.unsplash.com/photo-1507679799987-c73779587ccf?w=500",
                    20,
                    ethnic
                ),

                // Category 2: Spices & Organic Food
                new Product(
                    null,
                    "Kashmiri Mongra Saffron (Kesar) 5g",
                    "100% pure Grade A1 Kashmiri saffron strands harvested directly from Pampore valley.",
                    new BigDecimal("1799.00"),
                    "https://images.unsplash.com/photo-1608797178974-15b35a61dede?w=500",
                    50,
                    spices
                ),
                new Product(
                    null,
                    "Darjeeling Makaibari First Flush Tea 250g",
                    "Single estate, fragrant bio-organic Darjeeling orthodox black tea.",
                    new BigDecimal("750.00"),
                    "https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=500",
                    60,
                    spices
                ),
                new Product(
                    null,
                    "Malabar Tellicherry Black Peppercorns 200g",
                    "Extra-bold, sun-dried Tellicherry black peppercorns from Kerala coast.",
                    new BigDecimal("450.00"),
                    "https://images.unsplash.com/photo-1599940824399-b87987ceb72a?w=500",
                    80,
                    spices
                ),
                new Product(
                    null,
                    "Guntur Sannam Dried Red Chillies 500g",
                    "Fiery red Andhra chillies with natural color and authentic pungent aroma.",
                    new BigDecimal("320.00"),
                    "https://images.unsplash.com/photo-1588252303782-cb80119abd6d?w=500",
                    75,
                    spices
                ),
                new Product(
                    null,
                    "Organic Meghalaya Lakadong Turmeric 250g",
                    "High 7%+ curcumin organic turmeric powder ground from mountain rhizomes.",
                    new BigDecimal("399.00"),
                    "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=500",
                    90,
                    spices
                ),

                // Category 3: Handicrafts & Decor
                new Product(
                    null,
                    "Handcrafted Brass Nataraja Idol 10-inch",
                    "Solid brass sculpture depicting Lord Shiva as cosmic dancer, antique golden finish.",
                    new BigDecimal("2499.00"),
                    "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?w=500",
                    15,
                    handicrafts
                ),
                new Product(
                    null,
                    "Jaipur Blue Pottery Hand-painted Flower Vase",
                    "Crafted with quartz stone powder and natural Egyptian blue floral motifs.",
                    new BigDecimal("1299.00"),
                    "https://images.unsplash.com/photo-1578749556568-bc2c40e68b61?w=500",
                    25,
                    handicrafts
                ),
                new Product(
                    null,
                    "Kashmiri Walnut Wood Carved Jewellery Box",
                    "Hand-carved single block walnut wood box with velvet interior and secret brass latch.",
                    new BigDecimal("1850.00"),
                    "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=500",
                    30,
                    handicrafts
                ),
                new Product(
                    null,
                    "Bastar Dhokra Lost-Wax Tribal Musician Art",
                    "Bell-metal bell cast figure handcrafted by indigenous Bastar artisans.",
                    new BigDecimal("1699.00"),
                    "https://images.unsplash.com/photo-1544717305-2782549b5136?w=500",
                    18,
                    handicrafts
                ),
                new Product(
                    null,
                    "Madhubani Hand-painted Peacock Wall Frame",
                    "Authentic Mithila folk painting framed in solid teakwood border.",
                    new BigDecimal("2199.00"),
                    "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?w=500",
                    22,
                    handicrafts
                ),

                // Category 4: Indian Sweets & Mithai
                new Product(
                    null,
                    "Royal Kaju Katli Gift Hamper 500g",
                    "Made with premium Goan cashews and pure silver vark. Melt in the mouth festive mithai.",
                    new BigDecimal("699.00"),
                    "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=500",
                    40,
                    sweets
                ),
                new Product(
                    null,
                    "Bikaneri Sponge Rasgulla Tin 1kg",
                    "Soft cottage cheese dumplings soaked in light aromatic cardamom syrup.",
                    new BigDecimal("349.00"),
                    "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=500",
                    65,
                    sweets
                ),
                new Product(
                    null,
                    "Pure Desi Ghee Besan Laddu 500g",
                    "Slow-roasted chickpea flour roasted in A2 bilona cow ghee with almonds and pistachios.",
                    new BigDecimal("499.00"),
                    "https://images.unsplash.com/photo-1605197148560-6c9b329c32e9?w=500",
                    50,
                    sweets
                ),
                new Product(
                    null,
                    "Banarasi Lal Peda 400g",
                    "Rich caramelized khoya peda with hint of nutmeg from the ghats of Varanasi.",
                    new BigDecimal("420.00"),
                    "https://images.unsplash.com/photo-1541781774459-bb2af2f05b55?w=500",
                    35,
                    sweets
                ),
                new Product(
                    null,
                    "Mysore Pak Special Melt-in-Mouth 400g",
                    "Traditional royal recipe with pure ghee, sugar, and gram flour from Mysore palace kitchen tradition.",
                    new BigDecimal("380.00"),
                    "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=500",
                    45,
                    sweets
                ),

                // Category 5: Electronics & Smart Gadgets
                new Product(
                    null,
                    "boAt Rockerz 450 Pro Bluetooth Headphones",
                    "70 HRS playback, ASAP Charge, 40mm drivers with signature super extra bass.",
                    new BigDecimal("1999.00"),
                    "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500",
                    80,
                    electronics
                ),
                new Product(
                    null,
                    "Noise ColorFit Pulse 3 AMOLED Smartwatch",
                    "1.96-inch AMOLED display, BT calling, 100+ sports modes, 7 days battery life.",
                    new BigDecimal("2499.00"),
                    "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500",
                    55,
                    electronics
                ),
                new Product(
                    null,
                    "Fire-Boltt Ninja Call Pro Plus Watch",
                    "1.83-inch HD display with AI voice assistant, SpO2 & 24/7 heart rate tracker.",
                    new BigDecimal("1499.00"),
                    "https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=500",
                    70,
                    electronics
                ),
                new Product(
                    null,
                    "Portronics Konnect L 3-in-1 Fast Charging Cable",
                    "Braided fast charging cable with Type-C, Micro USB, and Lightning outputs.",
                    new BigDecimal("399.00"),
                    "https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=500",
                    120,
                    electronics
                ),
                new Product(
                    null,
                    "Mivi Fort S24 24W Bluetooth Soundbar",
                    "Dual passive radiators, 6 hours playtime, deep bass portable stereo soundbar.",
                    new BigDecimal("1799.00"),
                    "https://images.unsplash.com/photo-1545454675-3531b543be5d?w=500",
                    40,
                    electronics
                )
            ));

            System.out.println(">>> 25 Authentic Indian shopping products populated successfully across 5 categories!");
        }

        // 3. Seed Sample Orders for Single Customer
        if (orderRepository.count() == 0) {
            User singleCustomer = userRepository.findByUsername("customer").orElse(null);
            List<Product> products = productRepository.findAll();

            if (singleCustomer != null && !products.isEmpty()) {
                Product p1 = products.get(0);
                Product p2 = products.size() > 5 ? products.get(5) : products.get(0);

                com.example.indian_shopping_system.model.Order o1 = new com.example.indian_shopping_system.model.Order();
                o1.setUser(singleCustomer);
                o1.setOrderDate(java.time.LocalDateTime.now().minusDays(2));
                o1.setShippingAddress(singleCustomer.getAddress());
                o1.setPaymentMethod("UPI (Google Pay)");
                o1.setTransactionId("UPI-IND-8849204910");
                o1.setStatus(com.example.indian_shopping_system.model.OrderStatus.DELIVERED);

                com.example.indian_shopping_system.model.OrderItem i1 = new com.example.indian_shopping_system.model.OrderItem(null, o1, p1, 1, p1.getPrice());
                com.example.indian_shopping_system.model.OrderItem i2 = new com.example.indian_shopping_system.model.OrderItem(null, o1, p2, 2, p2.getPrice());
                o1.setItems(new java.util.ArrayList<>(List.of(i1, i2)));
                o1.setTotalAmount(p1.getPrice().multiply(BigDecimal.valueOf(1)).add(p2.getPrice().multiply(BigDecimal.valueOf(2))));

                orderRepository.save(o1);

                if (products.size() > 15) {
                    Product p3 = products.get(10);
                    Product p4 = products.get(15);

                    com.example.indian_shopping_system.model.Order o2 = new com.example.indian_shopping_system.model.Order();
                    o2.setUser(singleCustomer);
                    o2.setOrderDate(java.time.LocalDateTime.now().minusHours(6));
                    o2.setShippingAddress(singleCustomer.getAddress());
                    o2.setPaymentMethod("CARD (RuPay Prime)");
                    o2.setTransactionId("RUPAY-TXN-5928172910");
                    o2.setStatus(com.example.indian_shopping_system.model.OrderStatus.SHIPPED);

                    com.example.indian_shopping_system.model.OrderItem i3 = new com.example.indian_shopping_system.model.OrderItem(null, o2, p3, 1, p3.getPrice());
                    com.example.indian_shopping_system.model.OrderItem i4 = new com.example.indian_shopping_system.model.OrderItem(null, o2, p4, 2, p4.getPrice());
                    o2.setItems(new java.util.ArrayList<>(List.of(i3, i4)));
                    o2.setTotalAmount(p3.getPrice().multiply(BigDecimal.valueOf(1)).add(p4.getPrice().multiply(BigDecimal.valueOf(2))));

                    orderRepository.save(o2);
                }
            }
            System.out.println(">>> Sample orders seeded for single customer (customer)!");
        }
    }
}

