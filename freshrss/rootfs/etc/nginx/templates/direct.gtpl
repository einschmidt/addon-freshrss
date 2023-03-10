server {
    {{ if not .ssl }}
    listen 80 default_server;
    {{ else }}
    listen 80 default_server ssl http2;
    {{ end }}

    include /etc/nginx/includes/server_params.conf;

    {{ if .ssl }}
    include /etc/nginx/includes/ssl_params.conf;

    ssl_certificate /ssl/{{ .certfile }};
    ssl_certificate_key /ssl/{{ .keyfile }};
    {{ end }}

	# this regex is mandatory because of the API
    location ~ ^.+?\.php(/.*)?$ {
        fastcgi_pass 127.0.0.1:9001;
        fastcgi_read_timeout 900;
        fastcgi_split_path_info ^(.+\.php)(/.+)$;
        # By default, the variable PATH_INFO is not set under PHP-FPM
		# But FreshRSS API greader.php need it. If you have a “Bad Request” error, double check this var!
		# NOTE: the separate $path_info variable is required. For more details, see:
		# https://trac.nginx.org/nginx/ticket/321
		set $path_info $fastcgi_path_info;
		fastcgi_param PATH_INFO $path_info;
        fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
        include /etc/nginx/includes/fastcgi_params.conf;
    }
}