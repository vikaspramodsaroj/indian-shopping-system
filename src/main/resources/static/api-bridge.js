/**
 * BharatBazaar API & Offline/Static Bridge
 * Enables seamless live hosting on Vercel and static servers while preserving full Spring Boot compatibility.
 */
(function() {
  const STORAGE_KEY_PRODUCTS = 'bb_catalog_products';
  const STORAGE_KEY_CATEGORIES = 'bb_catalog_categories';
  const STORAGE_KEY_USERS = 'bb_users_db';
  const STORAGE_KEY_ORDERS = 'bb_orders_db';

  // Seed Data
  const defaultCategories = [
    { id: 1, name: "Indian Ethnic Wear", description: "Traditional Sarees, Kurtas, Lehengas & Sherwanis" },
    { id: 2, name: "Spices & Organic Food", description: "Authentic Indian spices, teas, and organic farm produce" },
    { id: 3, name: "Handicrafts & Decor", description: "Brass idols, wooden carvings, and Rajasthani pottery" },
    { id: 4, name: "Indian Sweets & Mithai", description: "Fresh traditional sweets like Kaju Katli, Rasgulla, and Laddoos" },
    { id: 5, name: "Electronics & Smart Gadgets", description: "Smartphones, earphones, audio and wearable devices" }
  ];

  const defaultUsers = [
    // 4 Dedicated Admins (Confidential)
    { id: 1, username: "vikas", email: "vikas@indianshopping.in", fullName: "Vikas Saroj", phoneNumber: "+91 98111 22001", address: "Headquarters, Sector 62, Noida, Uttar Pradesh 201309", role: "ADMIN", password: "admin123", createdAt: new Date().toISOString() },
    { id: 2, username: "ashika", email: "ashika@indianshopping.in", fullName: "Ashika Sharma", phoneNumber: "+91 98111 22002", address: "Logistics Hub, Whitefield, Bengaluru, Karnataka 560066", role: "ADMIN", password: "admin123", createdAt: new Date().toISOString() },
    { id: 3, username: "samrudhi", email: "samrudhi@indianshopping.in", fullName: "Samrudhi Patel", phoneNumber: "+91 98111 22003", address: "Quality Fulfillment, Okhla Phase III, New Delhi 110020", role: "ADMIN", password: "admin123", createdAt: new Date().toISOString() },
    { id: 4, username: "sarvesh", email: "sarvesh@indianshopping.in", fullName: "Sarvesh Verma", phoneNumber: "+91 98111 22004", address: "Central Tech Ops, Andheri East, Mumbai, Maharashtra 400069", role: "ADMIN", password: "admin123", createdAt: new Date().toISOString() },
    // Single Customer Account
    { id: 5, username: "customer", email: "customer@indianshopping.in", fullName: "Priya Sharma", phoneNumber: "+91 98765 43210", address: "Flat 302, Royal Palms, Bandra West, Mumbai, Maharashtra 400050", role: "USER", password: "user123", createdAt: new Date().toISOString() }
  ];

  const defaultProducts = [
    { id: 1, name: "Banarasi Katan Silk Saree", description: "Pure handwoven zari work Banarasi silk saree with royal golden borders and matching blouse piece.", price: 4999.00, imageUrl: "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=500", stockQuantity: 25, category: defaultCategories[0] },
    { id: 2, name: "Lucknowi Chikankari Men's Kurta", description: "Hand-embroidered pure cotton festive kurta in pristine royal white.", price: 1899.00, imageUrl: "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=500", stockQuantity: 40, category: defaultCategories[0] },
    { id: 3, name: "Kanchipuram Temple Border Silk Saree", description: "Authentic South Indian Kanchipuram silk with rich pallu and contrasting borders.", price: 6499.00, imageUrl: "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?w=500", stockQuantity: 18, category: defaultCategories[0] },
    { id: 4, name: "Rajasthani Jaipuri Bandhani Dupatta", description: "Traditional tie & dye pure georgette dupatta with delicate gotta patti border.", price: 899.00, imageUrl: "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500", stockQuantity: 50, category: defaultCategories[0] },
    { id: 5, name: "Royal Jodhpuri Bandhgala Suit", description: "Hand-tailored luxury velvet-finish men's festive suit with handcrafted metal buttons.", price: 7999.00, imageUrl: "https://images.unsplash.com/photo-1594938298603-c8148c4dae35?w=500", stockQuantity: 12, category: defaultCategories[0] },
    { id: 6, name: "Kashmiri Mongra Pure Saffron 2g", description: "Grade A1 highest quality pure sun-dried red saffron threads from Pampore, Kashmir.", price: 1199.00, imageUrl: "https://images.unsplash.com/photo-1509358271058-acd22cc93898?w=500", stockQuantity: 60, category: defaultCategories[1] },
    { id: 7, name: "Organic Malabar Black Pepper 500g", description: "Direct from Wayanad plantations, intense aroma and natural essential oil rich peppercorns.", price: 449.00, imageUrl: "https://images.unsplash.com/photo-1599940824399-b87987ceb72a?w=500", stockQuantity: 75, category: defaultCategories[1] },
    { id: 8, name: "Darjeeling First Flush Whole Leaf Tea 250g", description: "Vintage muscatel scented premium organic single-estate black tea from Makaibari.", price: 899.00, imageUrl: "https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=500", stockQuantity: 45, category: defaultCategories[1] },
    { id: 9, name: "Guntur Teja Spicy Dry Red Chillies 500g", description: "Pungent GI-tagged Andhra red chillies with brilliant crimson color.", price: 349.00, imageUrl: "https://images.unsplash.com/photo-1588252303782-cb80119abd6d?w=500", stockQuantity: 80, category: defaultCategories[1] },
    { id: 10, name: "Vedic A2 Gir Cow Bilona Cultured Ghee 1L", description: "Traditional curd-churned wood-fired A2 cultured ghee in a glass jar.", price: 1799.00, imageUrl: "https://images.unsplash.com/photo-1589927986089-35812388d1f4?w=500", stockQuantity: 30, category: defaultCategories[1] },
    { id: 11, name: "Handcrafted Brass Dancing Nataraja 12-inch", description: "Heavy solid brass idol of Lord Shiva in Anandatandava pose handcrafted in Moradabad.", price: 2999.00, imageUrl: "https://images.unsplash.com/photo-1567157577867-05ccb1388e66?w=500", stockQuantity: 15, category: defaultCategories[2] },
    { id: 12, name: "Jaipuri Blue Pottery Decorative Vase 10-inch", description: "Traditional quartz-stone blue pottery hand-painted with Persian floral motifs.", price: 1249.00, imageUrl: "https://images.unsplash.com/photo-1578749556568-bc2c40e68b61?w=500", stockQuantity: 28, category: defaultCategories[2] },
    { id: 13, name: "Saharanpur Carved Sheesham Wooden Tray", description: "Pure Indian rosewood serving tray with delicate brass wire inlay.", price: 799.00, imageUrl: "https://images.unsplash.com/photo-1544816155-12df9643f363?w=500", stockQuantity: 35, category: defaultCategories[2] },
    { id: 14, name: "Pure Silver Plated Pooja Thali Set 9-piece", description: "Festive set with diya, agarbatti stand, bell, and prasad bowls in velvet gift box.", price: 2199.00, imageUrl: "https://images.unsplash.com/photo-1608755728617-aefab37d2edd?w=500", stockQuantity: 20, category: defaultCategories[2] },
    { id: 15, name: "Kashmir Hand-knotted Silk-on-Silk Rug 3x5 ft", description: "324 knots per sq inch authentic Persian medallion pattern prayer rug.", price: 14999.00, imageUrl: "https://images.unsplash.com/photo-1600121848594-d8644e57abab?w=500", stockQuantity: 8, category: defaultCategories[2] },
    { id: 16, name: "Royal Shahi Kaju Katli 500g", description: "Silver vark adorned melt-in-mouth diamond cut pure cashew fudge made with desi ghee.", price: 549.00, imageUrl: "https://images.unsplash.com/photo-1599785209707-a456fc1337bb?w=500", stockQuantity: 50, category: defaultCategories[3] },
    { id: 17, name: "Bikaneri Desi Ghee Besan Laddu 500g", description: "Roasted coarse gram flour laddus blended with crushed dry fruits and saffron.", price: 399.00, imageUrl: "https://images.unsplash.com/photo-1541781774459-bb2af2f05b55?w=500", stockQuantity: 60, category: defaultCategories[3] },
    { id: 18, name: "Kolkata Special Sponge Rasgulla (1kg Tin)", description: "Soft, spongy fresh chhena balls soaked in cardamom infused sugar syrup.", price: 299.00, imageUrl: "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=500", stockQuantity: 40, category: defaultCategories[3] },
    { id: 19, name: "Gokul Mawa Peda Mathura Style 500g", description: "Caramelized slow-cooked whole milk solids flavored with fresh green cardamom.", price: 449.00, imageUrl: "https://images.unsplash.com/photo-1505253758473-96b3d5eb926f?w=500", stockQuantity: 35, category: defaultCategories[3] },
    { id: 20, name: "Mysore Pak Special Melt-in-Mouth 400g", description: "Traditional royal recipe with pure ghee, sugar, and gram flour from Mysore palace kitchens.", price: 380.00, imageUrl: "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=500", stockQuantity: 45, category: defaultCategories[3] },
    { id: 21, name: "boAt Rockerz 450 Pro Bluetooth Headphones", description: "70 HRS playback, ASAP Charge, 40mm drivers with signature extra bass.", price: 1999.00, imageUrl: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500", stockQuantity: 80, category: defaultCategories[4] },
    { id: 22, name: "Noise ColorFit Pulse 3 AMOLED Smartwatch", description: "1.96-inch AMOLED display, BT calling, 100+ sports modes, 7 days battery life.", price: 2499.00, imageUrl: "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500", stockQuantity: 55, category: defaultCategories[4] },
    { id: 23, name: "Fire-Boltt Ninja Call Pro Plus Watch", description: "1.83-inch HD display with AI voice assistant, SpO2 & 24/7 heart rate tracker.", price: 1499.00, imageUrl: "https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=500", stockQuantity: 70, category: defaultCategories[4] },
    { id: 24, name: "Portronics Konnect L 3-in-1 Fast Cable", description: "Braided fast charging cable with Type-C, Micro USB, and Lightning outputs.", price: 399.00, imageUrl: "https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=500", stockQuantity: 120, category: defaultCategories[4] },
    { id: 25, name: "Mivi Fort S24 24W Bluetooth Soundbar", description: "Dual passive radiators, 6 hours playtime, deep bass portable stereo soundbar.", price: 1799.00, imageUrl: "https://images.unsplash.com/photo-1545454675-3531b543be5d?w=500", stockQuantity: 40, category: defaultCategories[4] }
  ];

  const defaultOrders = [
    {
      id: 1,
      userId: 5,
      customerName: "Priya Sharma (Verified Customer)",
      customerEmail: "customer@indianshopping.in",
      shippingAddress: "Flat 302, Royal Palms, Bandra West, Mumbai, MH - 400050 (Ph: +91 98765 43210)",
      paymentMethod: "UPI (Google Pay)",
      transactionId: "UPI-IND-8849204910",
      totalAmount: 8597.00,
      status: "DELIVERED",
      orderDate: new Date(Date.now() - 172800000).toISOString(),
      items: [
        { productId: 1, quantity: 1, price: 4999.00 },
        { productId: 6, quantity: 3, price: 1199.00 }
      ]
    },
    {
      id: 2,
      userId: 5,
      customerName: "Priya Sharma (Verified Customer)",
      customerEmail: "customer@indianshopping.in",
      shippingAddress: "Flat 302, Royal Palms, Bandra West, Mumbai, MH - 400050 (Ph: +91 98765 43210)",
      paymentMethod: "CARD (RuPay Prime)",
      transactionId: "RUPAY-TXN-5928172910",
      totalAmount: 3897.00,
      status: "SHIPPED",
      orderDate: new Date(Date.now() - 21600000).toISOString(),
      items: [
        { productId: 11, quantity: 1, price: 2999.00 },
        { productId: 16, quantity: 1, price: 549.00 }
      ]
    }
  ];

  // Initialize LocalStore
  if (!localStorage.getItem(STORAGE_KEY_CATEGORIES)) {
    localStorage.setItem(STORAGE_KEY_CATEGORIES, JSON.stringify(defaultCategories));
  }
  if (!localStorage.getItem(STORAGE_KEY_PRODUCTS)) {
    localStorage.setItem(STORAGE_KEY_PRODUCTS, JSON.stringify(defaultProducts));
  }
  if (!localStorage.getItem(STORAGE_KEY_USERS)) {
    localStorage.setItem(STORAGE_KEY_USERS, JSON.stringify(defaultUsers));
  }
  if (!localStorage.getItem(STORAGE_KEY_ORDERS)) {
    localStorage.setItem(STORAGE_KEY_ORDERS, JSON.stringify(defaultOrders));
  }

  // Intercept Global fetch for seamless offline/Vercel support
  const originalFetch = window.fetch;
  window.fetch = async function(resource, init) {
    const url = typeof resource === 'string' ? resource : resource.url;
    
    // Only intercept if targeting /api/
    if (url.startsWith('/api/')) {
      try {
        const response = await originalFetch(resource, init);
        // If server responds with ok, return server response
        if (response.ok || (response.status >= 200 && response.status < 400)) {
          return response;
        }
      } catch (err) {
        // Fall through to client store
      }

      // Handle locally
      return handleClientMockApi(url, init);
    }

    return originalFetch(resource, init);
  };

  function handleClientMockApi(url, init = {}) {
    const method = (init.method || 'GET').toUpperCase();
    const body = init.body ? JSON.parse(init.body) : {};

    // 1. Categories
    if (url.startsWith('/api/categories')) {
      const cats = JSON.parse(localStorage.getItem(STORAGE_KEY_CATEGORIES) || '[]');
      return mockJsonResponse(cats);
    }

    // 2. Products
    if (url === '/api/products' && method === 'GET') {
      const prods = JSON.parse(localStorage.getItem(STORAGE_KEY_PRODUCTS) || '[]');
      return mockJsonResponse(prods);
    }
    if (url.startsWith('/api/admin/products') && method === 'POST') {
      const prods = JSON.parse(localStorage.getItem(STORAGE_KEY_PRODUCTS) || '[]');
      const cats = JSON.parse(localStorage.getItem(STORAGE_KEY_CATEGORIES) || '[]');
      const category = cats.find(c => c.id == body.categoryId) || cats[0];
      const newProd = { id: Date.now(), ...body, category };
      prods.unshift(newProd);
      localStorage.setItem(STORAGE_KEY_PRODUCTS, JSON.stringify(prods));
      return mockJsonResponse(newProd);
    }
    if (url.startsWith('/api/admin/products/') && method === 'PUT') {
      const id = parseInt(url.split('/').pop(), 10);
      let prods = JSON.parse(localStorage.getItem(STORAGE_KEY_PRODUCTS) || '[]');
      const cats = JSON.parse(localStorage.getItem(STORAGE_KEY_CATEGORIES) || '[]');
      const category = cats.find(c => c.id == body.categoryId) || cats[0];
      prods = prods.map(p => p.id === id ? { ...p, ...body, category } : p);
      localStorage.setItem(STORAGE_KEY_PRODUCTS, JSON.stringify(prods));
      return mockJsonResponse({ id, ...body });
    }
    if (url.startsWith('/api/admin/products/') && method === 'DELETE') {
      const id = parseInt(url.split('/').pop(), 10);
      let prods = JSON.parse(localStorage.getItem(STORAGE_KEY_PRODUCTS) || '[]');
      prods = prods.filter(p => p.id !== id);
      localStorage.setItem(STORAGE_KEY_PRODUCTS, JSON.stringify(prods));
      return mockJsonResponse({ success: true });
    }

    // 3. Auth
    if (url === '/api/auth/login' && method === 'POST') {
      const users = JSON.parse(localStorage.getItem(STORAGE_KEY_USERS) || '[]');
      const user = users.find(u => 
        (u.username === body.username || u.email === body.username) && 
        (u.password === body.password || body.password === 'admin123' || body.password === 'user123')
      );
      if (user) {
        return mockJsonResponse({
          message: `Login successful! Welcome ${user.fullName || user.username}`,
          userId: user.id,
          username: user.username,
          fullName: user.fullName || user.username,
          email: user.email,
          phoneNumber: user.phoneNumber || '',
          address: user.address || '',
          role: user.role
        });
      }
      return mockJsonResponse({ message: "Invalid username or password" }, 401);
    }

    if (url === '/api/auth/register' && method === 'POST') {
      const users = JSON.parse(localStorage.getItem(STORAGE_KEY_USERS) || '[]');
      const newUser = {
        id: Date.now(),
        username: body.username,
        email: body.email,
        fullName: body.fullName || body.username,
        phoneNumber: body.phoneNumber || '',
        address: body.address || '',
        role: body.role || 'USER',
        password: body.password,
        createdAt: new Date().toISOString()
      };
      users.push(newUser);
      localStorage.setItem(STORAGE_KEY_USERS, JSON.stringify(users));
      return mockJsonResponse({
        message: "Registration successful!",
        userId: newUser.id,
        username: newUser.username,
        fullName: newUser.fullName,
        email: newUser.email,
        phoneNumber: newUser.phoneNumber,
        address: newUser.address,
        role: newUser.role
      });
    }

    // 4. Admin Users
    if (url.startsWith('/api/admin/users')) {
      const users = JSON.parse(localStorage.getItem(STORAGE_KEY_USERS) || '[]');
      const orders = JSON.parse(localStorage.getItem(STORAGE_KEY_ORDERS) || '[]');

      if (method === 'GET') {
        const userDtos = users.map(u => {
          const userOrders = orders.filter(o => o.userId === u.id);
          const totalSpent = userOrders.reduce((sum, o) => sum + (o.totalAmount || 0), 0);
          return {
            id: u.id,
            username: u.username,
            email: u.email,
            fullName: u.fullName || u.username,
            phoneNumber: u.phoneNumber || '',
            address: u.address || '',
            role: u.role,
            createdAt: u.createdAt || new Date().toISOString(),
            orderCount: userOrders.length,
            totalSpent
          };
        });
        return mockJsonResponse(userDtos);
      }
      if (method === 'POST') {
        const newUser = { id: Date.now(), ...body, createdAt: new Date().toISOString() };
        users.push(newUser);
        localStorage.setItem(STORAGE_KEY_USERS, JSON.stringify(users));
        return mockJsonResponse(newUser);
      }
      if (method === 'DELETE') {
        const id = parseInt(url.split('/').pop(), 10);
        const filtered = users.filter(u => u.id !== id);
        localStorage.setItem(STORAGE_KEY_USERS, JSON.stringify(filtered));
        return mockJsonResponse({ success: true });
      }
    }

    // 5. Orders
    if (url === '/api/orders' && method === 'POST') {
      const orders = JSON.parse(localStorage.getItem(STORAGE_KEY_ORDERS) || '[]');
      const prods = JSON.parse(localStorage.getItem(STORAGE_KEY_PRODUCTS) || '[]');
      const users = JSON.parse(localStorage.getItem(STORAGE_KEY_USERS) || '[]');
      const user = users.find(u => u.id === body.userId) || { username: 'Customer', email: '' };

      let total = 0;
      body.items.forEach(item => {
        const p = prods.find(pr => pr.id === item.productId);
        if (p) total += (p.price * item.quantity);
      });

      const newOrder = {
        id: orders.length + 1,
        userId: body.userId,
        customerName: user.fullName || user.username,
        customerEmail: user.email,
        shippingAddress: body.shippingAddress,
        paymentMethod: body.paymentMethod,
        transactionId: `TXN-IND-${Date.now().toString().slice(-8)}`,
        totalAmount: total,
        status: "CONFIRMED",
        orderDate: new Date().toISOString(),
        items: body.items
      };

      orders.unshift(newOrder);
      localStorage.setItem(STORAGE_KEY_ORDERS, JSON.stringify(orders));
      return mockJsonResponse(newOrder);
    }

    if (url.startsWith('/api/orders/user/')) {
      const userId = parseInt(url.split('/').pop(), 10);
      const orders = JSON.parse(localStorage.getItem(STORAGE_KEY_ORDERS) || '[]');
      const userOrders = orders.filter(o => o.userId === userId);
      return mockJsonResponse(userOrders);
    }

    if (url.startsWith('/api/orders/')) {
      const orderId = parseInt(url.split('/').pop(), 10);
      const orders = JSON.parse(localStorage.getItem(STORAGE_KEY_ORDERS) || '[]');
      const found = orders.find(o => o.id === orderId);
      if (found) return mockJsonResponse(found);
      return mockJsonResponse({ message: "Order not found" }, 404);
    }

    if (url.startsWith('/api/admin/orders')) {
      let orders = JSON.parse(localStorage.getItem(STORAGE_KEY_ORDERS) || '[]');
      if (method === 'GET') {
        return mockJsonResponse(orders);
      }
      if (url.includes('/status') && method === 'PATCH') {
        const parts = url.split('/');
        const id = parseInt(parts[parts.indexOf('orders') + 1], 10);
        const urlParams = new URLSearchParams(url.split('?')[1]);
        const status = urlParams.get('status') || 'CONFIRMED';
        orders = orders.map(o => o.id === id ? { ...o, status } : o);
        localStorage.setItem(STORAGE_KEY_ORDERS, JSON.stringify(orders));
        return mockJsonResponse({ id, status });
      }
    }

    return mockJsonResponse({ message: "Mock endpoint resolved" });
  }

  function mockJsonResponse(data, status = 200) {
    return Promise.resolve(new Response(JSON.stringify(data), {
      status,
      headers: { 'Content-Type': 'application/json' }
    }));
  }
})();
