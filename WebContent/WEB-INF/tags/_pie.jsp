    <script type="text/javascript">
        $(document).ready(function () {
            //spiner para mostrar con las llamadas ajax
        	var spinner = $('#spinner');
            var ajax_cnt = 0;
            
            $(document).ajaxStart(function() {
                spinner.show();
                ajax_cnt++;
            });

            $(document).ajaxStop(function() {
                ajax_cnt--;
                if (ajax_cnt == 0)
                {
                    spinner.stop();
                    spinner.hide();
                    ajax_cnt = 0;
                }
            });
            
            //sidebar
            $('#sidebarCollapse').on('click', function () {
                $('#sidebar').toggleClass('active');
                $(this).toggleClass('active');
            });
        });
    </script>
