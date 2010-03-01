function disableGroup()
{
    document.getElementById('seqDB').disabled=true;
    document.getElementById('lc_t').disabled=true;
    //document.getElementById('aa').disabled=true;
    document.getElementById('lc_h').disabled=true;
    document.getElementById('lc_j').disabled=true;
    document.getElementById('psi_blast_filters').disabled=true;
}

function enableGroup()
{
	document.getElementById('seqDB').disabled=false;
    //document.getElementById('aa').disabled=false;
    document.getElementById('lc_h').disabled=false;
    document.getElementById('lc_j').disabled=false;
    document.getElementById('lc_t').disabled=false;
    document.getElementById('psi_blast_filters').disabled=false;
}
