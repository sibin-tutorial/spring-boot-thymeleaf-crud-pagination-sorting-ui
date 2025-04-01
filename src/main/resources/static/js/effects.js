// Add animation to elements when they come into view
document.addEventListener('DOMContentLoaded', function() {
    // Add hover effect to table rows
    const tableRows = document.querySelectorAll('table tr');
    tableRows.forEach(row => {
        row.addEventListener('mouseenter', function() {
            this.classList.add('animate__animated', 'animate__pulse');
        });
        
        row.addEventListener('mouseleave', function() {
            this.classList.remove('animate__animated', 'animate__pulse');
        });
    });
    
    // Add animation to buttons
    const buttons = document.querySelectorAll('.btn');
    buttons.forEach(button => {
        button.addEventListener('mouseenter', function() {
            this.classList.add('animate__animated', 'animate__rubberBand');
        });
        
        button.addEventListener('mouseleave', function() {
            this.classList.remove('animate__animated', 'animate__rubberBand');
        });
    });
    
    // Smooth scroll for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });
    
    // Toast notification for flash messages
    const flashMessage = document.querySelector('.alert');
    if (flashMessage) {
        setTimeout(() => {
            flashMessage.classList.add('animate__animated', 'animate__fadeOut');
            setTimeout(() => {
                flashMessage.remove();
            }, 1000);
        }, 3000);
    }
});