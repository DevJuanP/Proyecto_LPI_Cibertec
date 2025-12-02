<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<!-- Bootstrap 5 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

<style>
    :root {
        --sidebar-width: 250px;
        --topbar-height: 60px;
    }
    
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    .sidebar {
        position: fixed;
        top: var(--topbar-height);
        left: 0;
        height: calc(100vh - var(--topbar-height));
        width: var(--sidebar-width);
        background: #2c3e50;
        overflow-y: auto;
        transition: all 0.3s;
        z-index: 1000;
    }
    
    .sidebar-header {
        padding: 20px;
        background: #1a252f;
        color: white;
        border-bottom: 1px solid rgba(255,255,255,0.1);
    }
    
    .sidebar .nav-link {
        color: #ecf0f1;
        padding: 12px 20px;
        margin: 5px 10px;
        border-radius: 8px;
        transition: all 0.3s;
        display: flex;
        align-items: center;
    }
    
    .sidebar .nav-link:hover {
        background: #34495e;
        color: white;
        transform: translateX(5px);
    }
    
    .sidebar .nav-link.active {
        background: #3498db;
        color: white;
    }
    
    .sidebar .nav-link i {
        margin-right: 10px;
        font-size: 1.2rem;
        width: 25px;
    }
    
    .main-content {
        margin-left: var(--sidebar-width);
        margin-top: var(--topbar-height);
        padding: 30px;
        min-height: calc(100vh - var(--topbar-height));
        background: #f8f9fa;
    }
    
    .content-header {
        background: white;
        padding: 20px;
        border-radius: 10px;
        margin-bottom: 30px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    
    .card {
        border: none;
        border-radius: 10px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        margin-bottom: 20px;
    }
    
    .card-header {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        border-radius: 10px 10px 0 0 !important;
        padding: 15px 20px;
    }
    
    .stat-card {
        transition: transform 0.3s;
    }
    
    .stat-card:hover {
        transform: translateY(-5px);
    }
    
    .sidebar-toggle {
        display: none;
    }
    
    @media (max-width: 768px) {
        .sidebar {
            left: calc(-1 * var(--sidebar-width));
        }
        
        .sidebar.show {
            left: 0;
        }
        
        .main-content {
            margin-left: 0;
        }
        
        .sidebar-toggle {
            display: block;
        }
    }
</style>
