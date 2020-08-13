<template>
    <div>
        <div id="container" class="background"></div>
        <!--<div id="vertexshader"></div>
        <div id="fragmentshader"></div>-->
        <script type="x-shader/x-vertex" id="vertexshader">
			attribute float scale;
			void main() {
				vec4 mvPosition = modelViewMatrix * vec4( position, 1.0 );
				gl_PointSize = scale * ( 300.0 / - mvPosition.z );
				gl_Position = projectionMatrix * mvPosition;
			}
		</script>
        <script type="x-shader/x-fragment" id="fragmentshader">
			uniform vec3 color;
			void main() {
				if ( length( gl_PointCoord - vec2( 0.5, 0.5 ) ) > 0.475 ) discard;
				gl_FragColor = vec4( color, 1.0 );
			}
		</script>
    </div>

</template>

<script>
    import * as Three from 'three';

    export default {
        name: "Canvas",
        data() {
            return {
                camera: null,
                scene: null,
                renderer: null,
                particles: null,
                AMOUNTX: 50,
                AMOUNTY: 50,
                SEPARATION: 100,
                mouseX: 0,
                mouseY: 0,
                count: 0,
                windowHalfX: window.innerWidth / 2,
                windowHalfY: window.innerHeight / 2,
            }
        },
        methods: {
            removeListeners() {
                return null;
            },

            init() {
                const container = document.getElementById('container');
                // const container = this.$refs.container;
                this.camera = new Three.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 10000);
                this.camera.position.z = 1000;
                this.scene = new Three.Scene();

                const numParticles = this.AMOUNTX * this.AMOUNTY;
                const positions = new Float32Array(numParticles * 3);
                const scales = new Float32Array(numParticles);

                let i = 0;
                let j = 0;

                for (let ix = 0; ix < this.AMOUNTX; ix++) {
                    for (let iy = 0; iy < this.AMOUNTY; iy++) {
                        positions[i] = ix * this.SEPARATION - ((this.AMOUNTX * this.SEPARATION) / 2); // x
                        positions[i + 1] = 0; // y
                        positions[i + 2] = iy * this.SEPARATION - ((this.AMOUNTY * this.SEPARATION) / 2);// z
                        scales[j] = 1;
                        i += 3;
                        j++
                    }
                }

                const geometry = new Three.BufferGeometry();
                geometry.addAttribute('position', new Three.BufferAttribute(positions, 3));
                geometry.addAttribute('scale', new Three.BufferAttribute(scales, 1));
                const material = new Three.ShaderMaterial({
                    uniforms: {
                        color: {
                            value: new Three.Color(0xffffff)
                        }
                    },
                    vertexShader: document.getElementById('vertexshader').textContent,
                    fragmentShader: document.getElementById('fragmentshader').textContent
                });
                //
                this.particles = new Three.Points(geometry, material);
                this.scene.add(this.particles);

                this.renderer = new Three.WebGLRenderer({ antialias: true });
                this.renderer.setPixelRatio(window.devicePixelRatio || 1);
                this.renderer.setSize(window.innerWidth, window.innerHeight);
                this.renderer.setClearColor(parseInt('#223151'.substr(1), 16));
                container.appendChild(this.renderer.domElement);

                document.addEventListener('mousemove', this.onDocumentMouseMove, false);
                container.addEventListener('touchstart', this.onDocumentTouchStart, false);
                container.addEventListener('touchmove', this.onDocumentTouchMove, false);

                window.addEventListener('resize', this.onWindowResize, false);

                this.removeListeners = () => {
                    document.removeEventListener('mousemove', this.onDocumentMouseMove, false);
                    container.removeEventListener('touchstart', this.onDocumentTouchStart, false);
                    container.removeEventListener('touchmove', this.onDocumentTouchMove, false);
                    window.removeEventListener('resize', this.onWindowResize, false)
                }
            },
            onWindowResize() {
                this.windowHalfX = window.innerWidth / 2;
                this.windowHalfY = window.innerHeight / 2;

                this.camera.aspect = window.innerWidth / window.innerHeight;
                this.camera.updateProjectionMatrix();
                this.renderer.setSize(window.innerWidth, window.innerHeight)
            },
            onDocumentMouseMove(event) {
                this.mouseX = event.clientX - this.windowHalfX;
                this.mouseY = event.clientY - this.windowHalfY;
            },
            onDocumentTouchStart(event) {
                if (event.touches.length === 1) {
                    event.preventDefault();
                    this.mouseX = event.touches[0].pageX - this.windowHalfX;
                    this.mouseY = event.touches[0].pageY - this.windowHalfY;
                }
            },
            onDocumentTouchMove(event) {
                if (event.touches.length === 1) {
                    event.preventDefault();
                    this.mouseX = event.touches[0].pageX - this.windowHalfX;
                    this.mouseY = event.touches[0].pageY - this.windowHalfY;
                }
            },
            animate() {
                requestAnimationFrame(this.animate);
                this.render();
            },
            render() {
                this.camera.position.x += (this.mouseX - this.camera.position.x) * .05;
                this.camera.position.y += (-this.mouseY - this.camera.position.y) * .05;
                this.camera.lookAt(this.scene.position);
                const positions = this.particles.geometry.attributes.position.array;
                const scales = this.particles.geometry.attributes.scale.array;

                let i = 0;
                let j = 0;
                for (let ix = 0; ix < this.AMOUNTX; ix++) {
                    for (let iy = 0; iy < this.AMOUNTY; iy++) {
                        positions[i + 1] = (Math.sin((ix + this.count) * 0.3) * 50) + (Math.sin((iy + this.count) * 0.5) * 50);
                        scales[j] = (Math.sin((ix + this.count) * 0.3) + 1) * 8 + (Math.sin((iy + this.count) * 0.5) + 1) * 8;
                        i += 3;
                        j++;
                    }
                }
                this.particles.geometry.attributes.position.needsUpdate = true;
                this.particles.geometry.attributes.scale.needsUpdate = true;

                this.renderer.render(this.scene, this.camera);
                this.count += 0.1;
            },
        },
        beforeDestroy: function() {
            if (this.removeListeners) {
                this.removeListeners()
            }
        },
        mounted: function() {
            this.init();
            this.animate();
        }
    }
</script>


<style scoped>
    .background {
        position: absolute;
        top: 0;
        left: 0;
        display: block;
    }
</style>
